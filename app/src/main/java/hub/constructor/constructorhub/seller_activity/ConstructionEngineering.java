package hub.constructor.constructorhub.seller_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hub.constructor.constructorhub.Adapter.ExpandableListAdapter;
import hub.constructor.constructorhub.R;

public class ConstructionEngineering extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_engineering);


        listView = findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Architectural engineering");
        listDataHeader.add("Rebar");
        listDataHeader.add("Blueprint");
        listDataHeader.add("Arch");

        List<String> edmtDev = new ArrayList<>();
        edmtDev.add("Architectural Engineering, also known as Building Engineering or Architecture Engineering, is the application of engineering principles and technology to building design and construction. Definitions of an architectural engineer may refer to:\n" +
                "\n" +
                "    An engineer in the structural, mechanical, electrical, construction or other engineering fields of building design and construction.\n" +
                "    A licensed engineering professional in parts of the United States.\n" +
                "    Architectural engineers are those who work with other engineers and architects for the designing and construction of buildings.In some countries, the practice of architecture includes planning, designing and overseeing the building's construction, and architecture, as a profession providing architectural services, is referred to as \"architectural engineering\". In Japan, a \"first-class architect\" plays the dual role of architect and building engineer, although the services of a licensed \"structural design first-class architect\"(構造設計一級建築士) are required for buildings over a certain scale.[3]\n" +
                "\n" +
                "In some languages, such as Korean and Arabic, \"architect\" is literally translated as \"architectural engineer\". In some countries, an \"architectural engineer\" (such as the ingegnere edile in Italy) is entitled to practice architecture and is often referred to as an architect.[4][not in citation given] These individuals are often also structural engineers. In other countries, such as Germany, Austria, Iran, and most of the Arab countries, architecture graduates receive an engineering degree (Dipl.-Ing. – Diplom-Ingenieur).[5]\n" +
                "\n" +
                "In Spain, an \"architect\" has a technical university education and legal powers to carry out building structure and facility projects.[6]\n" +
                "\n" +
                "In Brazil, architects and engineers used to share the same accreditation process (Conselho Federal de Engenheiros, Arquitetos e Agrônomos (CONFEA) – Federal Council of Engineering, Architecture and Agronomy). Now the Brazilian architects and urbanists have their own accreditation process (CAU – Architecture and Urbanism Council). Besides traditional architecture design training, Brazilian architecture courses also offer complementary training in engineering disciplines such as structural, electrical, hydraulic and mechanical engineering. After graduation, architects focus in architectural planning, yet they can be responsible to the whole building, when it concerns to small buildings (except in electric wiring, where the architect autonomy is limited to systems up to 30kVA, and it has to be done by an Electrical Engineer), applied to buildings, urban environment, built cultural heritage, landscape planning, interiorscape planning and regional planning.[7][8]\n" +
                "\n" +
                "In Greece licensed architectural engineers are graduates from architecture faculties that belong to the Polytechnic University,[9] obtaining an \"Engineering Diploma\". They graduate after 5 years of studies and are fully entitled architects once they become members of the Technical Chamber of Greece (TEE – Τεχνικό Επιμελητήριο Ελλάδος).[10][11] The Technical Chamber of Greece has more than 100,000 members encompassing all the engineering disciplines as well as architecture. A prerequisite for being a member is to be licensed as a qualified engineer or architect and to be a graduate of an engineering and architecture schools of a Greek university, or of an equivalent school from abroad. The Technical Chamber of Greece is the authorized body to provide work licenses to engineers of all disciplines as well as architects, graduated in Greece or abroad. The license is awarded after examinations. The examinations take place three to four times a year. " +
                "The Engineering Diploma equals a master's degree in ECTS units (300) according to the Bologna Accords.[12] ");



        listHash.put(listDataHeader.get(0),edmtDev);
    }
}
