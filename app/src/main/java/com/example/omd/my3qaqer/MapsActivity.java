package com.example.omd.my3qaqer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button ShowBtn;
    private DatabaseReference dRef;
    List<String> pharmacyKeysList;
    List<Drug_Model> drug_model_List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        dRef = FirebaseDatabase.getInstance().getReference();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Flag.setFlag(false);
        Intent intent = getIntent();
        drug_model_List = (List<Drug_Model>) intent.getSerializableExtra("drugmodel");
        pharmacyKeysList = (List<String>) intent.getSerializableExtra("pharmacyKeys");
        Toast.makeText(this,drug_model_List.size()+"", Toast.LENGTH_SHORT).show();
        ShowBtn = (Button) findViewById(R.id.ViewResultBtn);

        ShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this,Result_Activity.class);
                i.putExtra("drugmodel", (Serializable) drug_model_List);
                i.putExtra("pharmacyKeys", (Serializable) pharmacyKeysList);
                startActivity(i);
            }
        });

    }



        /*LatLng myloc = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(myloc).title("iam here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));*/
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       /* mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.marker_custom,null);
                Toast.makeText(MapsActivity.this,marker.getSnippet(), Toast.LENGTH_SHORT).show();
                ImageView DrugImage       = (ImageView) view.findViewById(R.id.Map_Drugimage);
                TextView  Drugname        = (TextView) view.findViewById(R.id.Map_Drugname);
                TextView  Drugconcentrate = (TextView) view.findViewById(R.id.Map_Drugconentrate);
                TextView  Drugtype        = (TextView) view.findViewById(R.id.Map_Drugtype);
                TextView  pharmacyname    = (TextView) view.findViewById(R.id.Map_pharmacyname);
                TextView  pharmacyphone   = (TextView) view.findViewById(R.id.Map_pharmacyphone);
                TextView  pharmacylocation= (TextView) view.findViewById(R.id.Map_pharmacylocation);
                Toast.makeText(MapsActivity.this,marker.getPosition()+"", Toast.LENGTH_SHORT).show();
                return view;
            }
        });*/
        GetLocation(pharmacyKeysList);

       /* for (Location_Model l_model :loc_List)
        {
            Double lat1 =Double.valueOf(l_model.getLatitude());
            Double lon2 = Double.valueOf(l_model.getLongitude());
            LatLng drug_loc = new LatLng(lat1,lon2);
            mMap.addMarker(new MarkerOptions().position(drug_loc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.marker_custom,null);
                    ImageView DrugImage       = (ImageView) view.findViewById(R.id.Map_Drugimage);
                    TextView  Drugname        = (TextView) view.findViewById(R.id.Map_Drugname);
                    TextView  Drugconcentrate = (TextView) view.findViewById(R.id.Map_Drugconentrate);
                    TextView  Drugtype        = (TextView) view.findViewById(R.id.Map_Drugtype);
                    TextView  pharmacyname    = (TextView) view.findViewById(R.id.Map_pharmacyname);
                    TextView  pharmacyphone   = (TextView) view.findViewById(R.id.Map_pharmacyphone);
                    TextView  pharmacylocation= (TextView) view.findViewById(R.id.Map_pharmacylocation);

                    return view;
                }
            });
        }*/

        // Add a marker in Sydney and move the camera

    }

    private void GetLocation(final List<String> pharmacyKeysList) {
        DatabaseReference locationRef = dRef.child(Firebase_DataBase_Holder.location_Info);
        locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null)
                {
                    for (String s:pharmacyKeysList)
                    {
                        Location_Model location_model = dataSnapshot.child(s).getValue(Location_Model.class);
                         Double lat = Double.valueOf(location_model.getLatitude());
                         Double lon = Double.valueOf(location_model.getLongitude());
                         mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
     /* private void GetPharmacy_Info(final List<String> pharmacy_keys, final TextView pharmacyname, final TextView pharmacyphone, final TextView pharmacylocation) {
        DatabaseReference pharmacyRef = dRef.child(Firebase_DataBase_Holder.pharmacy_Info);
        pharmacyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null)
                {

                    for (String s:pharmacy_keys)
                    {
                        Pharmacy_Model pModel = dataSnapshot.child(s).getValue(Pharmacy_Model.class);
                        pharmacyname.setText(pModel.getPharmacy_name());
                        pharmacyphone.setText(pModel.getPharmacy_phone());
                        pharmacylocation.setText(pModel.getPharmacy_location());
                        Toast.makeText(MapsActivity.this,pModel.getPharmacy_name(), Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/


}
