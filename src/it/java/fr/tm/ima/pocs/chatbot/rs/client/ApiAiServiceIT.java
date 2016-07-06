package fr.tm.ima.pocs.chatbot.rs.client;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.collection.IsMapContaining;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.tm.ima.pocs.chatbot.controller.ChatMessage;

//@RunWith(SpringJUnit4ClassRunner.class)
public class ApiAiServiceIT {

    private static final String TYPE_VEHICULE = "typeVehicule";

    private static final String SWITCH_TO_HUMAN = "switch_to_human";

    private static final String NOM_SOCIETAIRE = "GIRARD";

    private static final String NUMERO_IMMATRICULATION = "11-22-33-44";

    private static final String ACCIDENT_MATERIEL = "accident_materiel";

    private static final String ASSISTANCE = "assistance";

    static ApiAiService apiAiService = new ApiAiService();

    static List<String> accueilResponse;

    private String sessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("http.proxyHost", "proxyweb.ima.intra");
        System.setProperty("http.proxyPort", "3128");
        System.setProperty("https.proxyHost", "proxyweb.ima.intra");
        System.setProperty("https.proxyPort", "3128");

        apiAiService.setUrl("https://api.api.ai/v1");
        apiAiService.setClientKey("285e80ffd56949149ac8700465e2f29d");

        accueilResponse = new ArrayList<String>();
        accueilResponse.add("Bonjour, en quoi puis-je vous aider ?");
        accueilResponse.add("Bonjour, en quoi puis-je vous être utile ?");
        accueilResponse.add("Bonjour, que vous arrive-t-il ?");
    }

    @Test
    public void accidentCorporelSansContext() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        chatMessage.setMessage("je suis blessé");

        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("000_assistance_accident_corporel"));

        // Vérification de l'action
        assertThat(response.getResult().getAction(), equalTo("stop_conversation"));

        // Vérification du reset du context
        assertThat(response.getResult().getContexts(), is(empty()));
    }

    @Test
    public void accidentCorporelAvecContext() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        chatMessage.setMessage("je viens d'avoir un accident");

        response = apiAiService.getApiAiResponse(chatMessage);
        // Vérification
        checkAccidentIntention(response);

        // Présence de blessé ?
        chatMessage.setMessage("oui");

        response = apiAiService.getApiAiResponse(chatMessage);
        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("001_assistance_accident_corporel"));

        // Vérification de l'action
        assertThat(response.getResult().getAction(), equalTo("stop_conversation"));

        // Vérification du reset du context
        assertThat(response.getResult().getContexts(), is(empty()));
    }

    @Test
    public void accidentMaterielVehicule() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        chatMessage.setMessage("je viens d'avoir un accrochage avec ma voiture");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification
        checkAccidentIntention(response);

        // Présence de blessé ?
        chatMessage.setMessage("non");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("001_assistance_accident_materiel"));

        // Vérification du context
        assertThat(response.getResult().getContexts(),
                hasItems(new ApiAiContext(ASSISTANCE), new ApiAiContext(ACCIDENT_MATERIEL)));

        // Précision sur le type d'accident...
        chatMessage.setMessage("ma caisse a heurté un arbre");
        response = apiAiService.getApiAiResponse(chatMessage);
        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(),
                equalTo("002_assistance_accident_materiel_vehicule"));

        // Vérification du context
        assertThat(
                response.getResult().getContexts(),
                hasItems(new ApiAiContext(ASSISTANCE), new ApiAiContext("vehicule"), new ApiAiContext("societaire"),
                        new ApiAiContext(ACCIDENT_MATERIEL)));
        // Vérification des paramètres
        assertThat(response.getResult().getParameters(), hasEntry(TYPE_VEHICULE, "voiture"));

        chatMessage.setMessage(NUMERO_IMMATRICULATION);
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("003_assistance_vehicule_infoSoc"));
        assertThat(response.getResult().getParameters(), hasEntry("numeroImmatriculation", NUMERO_IMMATRICULATION));
        assertThat(response.getResult().isActionIncomplete(), equalTo(true));

        chatMessage.setMessage(NOM_SOCIETAIRE);
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        checkIntentionInfoSocietaire(response, "voiture", "accident");
    }

    @Test
    public void accidentMaterielHabitation() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        chatMessage.setMessage("J'ai eu un accident");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification
        checkAccidentIntention(response);

        // Présence de blessé ?
        chatMessage.setMessage("non");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("001_assistance_accident_materiel"));

        // Vérification du context
        assertThat(response.getResult().getContexts(),
                hasItems(new ApiAiContext(ASSISTANCE), new ApiAiContext(ACCIDENT_MATERIEL)));

        // Précision sur le type d'accident...
        chatMessage.setMessage("il y a de l'eau dans sous-sol de ma baraque");
        response = apiAiService.getApiAiResponse(chatMessage);
        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("000_assistance_habitation"));

        // Vérification des paramètres
        assertThat(response.getResult().getParameters(), hasEntry("typeHabitation", "maison"));

        // Vérification de l'action
        assertThat(response.getResult().getAction(), equalTo("stop_conversation"));

        // Vérification du reset du context
        assertThat(response.getResult().getContexts(), is(empty()));
    }

    @Test
    public void accidentMaterielAutre() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        chatMessage.setMessage("je viens d'avoir un accident");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification
        checkAccidentIntention(response);

        // Présence de blessé ?
        chatMessage.setMessage("non");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("001_assistance_accident_materiel"));

        // Vérification du context
        assertThat(response.getResult().getContexts(),
                hasItems(new ApiAiContext(ASSISTANCE), new ApiAiContext(ACCIDENT_MATERIEL)));

        // Précision sur le type d'accident...
        chatMessage.setMessage("je ne sais pas");
        response = apiAiService.getApiAiResponse(chatMessage);
        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(),
                equalTo("002_assistance_accident_materiel_hors_vehicule"));

        // Vérification de l'action
        assertThat(response.getResult().getAction(), equalTo(SWITCH_TO_HUMAN));

        // Vérification du reset du context
        assertThat(response.getResult().getContexts(), is(empty()));
    }
    
    /**
     * Vérification de la réponse en cas d'accident.
     * 
     * @param response
     */
    private void checkAccidentIntention(ApiAiResponse response) {
        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("000_assistance_accident"));
        // Vérification du context
        assertThat(response.getResult().getContexts(), hasItem(new ApiAiContext("question_accident_blesse")));
    }

    private void checkIntentionInfoSocietaire(ApiAiResponse response, String expectedTypeVehicule,
            String expectedTypeAssistance) {
        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("003_assistance_vehicule_infoSoc"));
        assertThat(response.getResult().isActionIncomplete(), equalTo(false));
        // Vérification des paramètres
        assertThat(response.getResult().getParameters(), hasEntry("numeroImmatriculation", NUMERO_IMMATRICULATION));
        assertThat(response.getResult().getParameters(), hasEntry("nomSocietaire", NOM_SOCIETAIRE));
        assertThat(response.getResult().getParameters(), hasEntry(TYPE_VEHICULE, expectedTypeVehicule));
        assertThat(response.getResult().getParameters(), hasEntry("typeAssistance", expectedTypeAssistance));

        // Vérification de l'action
        assertThat(response.getResult().getAction(), equalTo(SWITCH_TO_HUMAN));

        // Vérification du reset du context
        assertThat(response.getResult().getContexts(), is(empty()));
    }

 

    @Test
    public void assistanceHabitation() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        chatMessage.setMessage("La chaudière de mon appartement ne fonctionne plus");
        response = apiAiService.getApiAiResponse(chatMessage);
        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("000_assistance_habitation"));

        // Vérification de l'action
        assertThat(response.getResult().getAction(), equalTo("stop_conversation"));

        // Vérification du reset du context
        assertThat(response.getResult().getContexts(), is(empty()));
    }

    @Test
    public void assistanceAideDomicile() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        // TODO gérer au niveau du service la suppression des accents
        chatMessage.setMessage("je voudrais une femme de ménage");
        response = apiAiService.getApiAiResponse(chatMessage);
        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("000_assistance_aide_domicile"));

        // Vérification de l'action
        assertThat(response.getResult().getAction(), equalTo(SWITCH_TO_HUMAN));

        // Vérification du reset du context
        assertThat(response.getResult().getContexts(), is(empty()));
    }

    @Test
    public void assistanceVehiculeProblemeConnu() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        chatMessage.setMessage("mon scooter est en panne, ma batterie est à plat");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("000_assistance_vehicule_pb_connu"));
        // Vérification du context
        assertThat(response.getResult().getContexts(),
                hasItems(new ApiAiContext(ASSISTANCE), new ApiAiContext("vehicule"), new ApiAiContext("societaire")));
        // Vérification des paramètres
        assertThat(response.getResult().getParameters(), hasEntry(TYPE_VEHICULE, "deux-roues"));

        chatMessage.setMessage(NUMERO_IMMATRICULATION);
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("003_assistance_vehicule_infoSoc"));
        assertThat(response.getResult().getParameters(), hasEntry("numeroImmatriculation", NUMERO_IMMATRICULATION));
        assertThat(response.getResult().isActionIncomplete(), equalTo(true));

        chatMessage.setMessage(NOM_SOCIETAIRE);
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("003_assistance_vehicule_infoSoc"));
        assertThat(response.getResult().isActionIncomplete(), equalTo(false));

        // Vérification de l'intention
        checkIntentionInfoSocietaire(response, "deux-roues", "technique");
    }

    @Test
    public void assistanceVehiculeProblemeInconnu() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId());
        ApiAiResponse response;

        chatMessage.setMessage("pouvez vous me dépanner");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("000_assistance_vehicule_pb_inconnu"));
        // Vérification du context
        assertThat(response.getResult().getContexts(),
                hasItems(new ApiAiContext(ASSISTANCE), new ApiAiContext("vehicule")));
        // Vérification des paramètres
        assertThat(response.getResult().getParameters(), hasEntry(TYPE_VEHICULE, "véhicule"));

        chatMessage.setMessage("je n'ai plus d'essence");
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(),
                equalTo("001_assistance_vehicule_investigation_pb_ok"));
        // Vérification du context
        assertThat(response.getResult().getContexts(),
                hasItems(new ApiAiContext(ASSISTANCE), new ApiAiContext("vehicule"), new ApiAiContext("societaire")));
        // Vérification des paramètres
        assertThat(response.getResult().getParameters(), hasEntry(TYPE_VEHICULE, "véhicule"));

        chatMessage.setMessage(NUMERO_IMMATRICULATION);
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("003_assistance_vehicule_infoSoc"));
        assertThat(response.getResult().getParameters(), hasEntry("numeroImmatriculation", NUMERO_IMMATRICULATION));
        assertThat(response.getResult().isActionIncomplete(), equalTo(true));

        chatMessage.setMessage(NOM_SOCIETAIRE);
        response = apiAiService.getApiAiResponse(chatMessage);

        // Vérification de l'intention
        assertThat(response.getResult().getMetadata().getIntentName(), equalTo("003_assistance_vehicule_infoSoc"));
        assertThat(response.getResult().isActionIncomplete(), equalTo(false));

        // Vérification de l'intention
        checkIntentionInfoSocietaire(response, "véhicule", "de panne sèche");
    }

    @Ignore
    @Test
    public void getEntitie() {
        apiAiService.getEntitie("type_assistance_vehicule");
    }
}
