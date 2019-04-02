package hub.constructor.constructorhub.nav.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hub.constructor.constructorhub.Adapter.ExpandableListAdapter;
import hub.constructor.constructorhub.R;

public class DeveloperActivity extends AppCompatActivity {


    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

            listView = findViewById(R.id.lvExp);
            initData();
            listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
            listView.setAdapter(listAdapter);
    }

        private void initData() {
            listDataHeader = new ArrayList<>();
            listHash = new HashMap<>();

            listDataHeader.add("Amin Mohammad Lands Development Ltd.");
            listDataHeader.add("Building Technology & Ideas Ltd.");
            listDataHeader.add("Concord Engg. & Construction");
            listDataHeader.add("Ideal Home Builders Ltd.");

            List<String> edmtDev = new ArrayList<>();
            edmtDev.add("Address: House # 103, Road # 9/A, Dhanmondi R/A");
            edmtDev.add("City: Dhaka - 1209");
            edmtDev.add("Country: Bangladesh");
            edmtDev.add("Telephone: +880-2-8117171, 9113355, 9129494");
            edmtDev.add("Fax: +880-2-8117373");
            edmtDev.add("Description: Constructing power station,highrise building,apartment,steel meal, road etc.");

            List<String> androidStudio = new ArrayList<>();
            androidStudio.add("Address: Celebration Point, Road-113/A, Plot-3&5, Gulshan-2");
            androidStudio.add("City: Dhaka - 1212");
            androidStudio.add("Country: Bangladesh");
            androidStudio.add("Telephone: +88 09613 191919 ");
            androidStudio.add("Website: http://www.btibd.com");
            androidStudio.add("Contact Person: bti Front Desk");
            androidStudio.add("Description: A Real Estate Developer Established in 1984. " +
                    "bti has had a rich history throughout its years of being a real estate developer company in Bangladesh." +
                    "It has survived and prospered in even the worst political and economic conditions. " +
                    "How did we do it? Simple. We lived by our core values and all our");


            List<String> xamarin = new ArrayList<>();
            xamarin.add("Address: Concord Center, 43, North C/A, Gulshan-2");
            xamarin.add("City: Dhaka - 1212");
            xamarin.add("Country: Bangladesh");
            xamarin.add("Telephone: +880-2-8814030, 8814028");
            xamarin.add("Fax: +880-2-8823552");
            xamarin.add("Description: We represent many building & land developer information, etc.");


            List<String> uwp = new ArrayList<>();
            uwp.add("Address: 157 Shantinagar (1st floor)");
            uwp.add("City: Dhaka - 1217");
            uwp.add("Country: Bangladesh");
            uwp.add("Telephone: +880-2-9340888, 9335006");
            uwp.add("Fax: +880-2-9337420");
            uwp.add("Website: http://www.iccbd.com");
            uwp.add("Description: We represent many building & land developer information, etc.");


            listHash.put(listDataHeader.get(0),edmtDev);
            listHash.put(listDataHeader.get(1),androidStudio);
            listHash.put(listDataHeader.get(2),xamarin);
            listHash.put(listDataHeader.get(3),uwp);
        }
}


