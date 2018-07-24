package sg.edu.rp.webservices.firebasehelloworld;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Declare variable
    private TextView tvMessageText, tvMessagePriority;
    private EditText etMessageText, etMessagePriority;
    private Button btnMessage;

    // TODO: Task 1 - Declare Firebase variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference messagePOJOReference;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get UI element
        tvMessageText = (TextView) findViewById(R.id.textViewMessageText);
        tvMessagePriority = (TextView) findViewById(R.id.textViewMessagePriority);
        etMessageText = (EditText) findViewById(R.id.editTextMessageText);
        etMessagePriority = (EditText) findViewById(R.id.editTextMessagePriority);
        btnMessage = (Button) findViewById(R.id.buttonAdd);
        // TODO: Task 2 - Get Firebase database instance and reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        messagePOJOReference = firebaseDatabase.getReference("messagePOJO");


        // TODO: Task 3 - Add a value event listener to the "message" node
        messagePOJOReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // TODO: Task 4 - Get the latest value from the dataSnapshot and display on the UI using the EditText message
                // This method will get fired everytime the "message" value updates in the realtime database. We're getting our data back as a DataSnapshot
                Message message = dataSnapshot.getValue(Message.class);
                if (message != null) {
                    tvMessageText.setText(message.getText());
                    tvMessagePriority.setText("Priority is " + message.getPriority());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // This method will be invoked if there is any error
                Log.e(TAG, "Database error occurred", databaseError.toException());

            }
        });


        // TODO: Task 5 - Update UI elements, and then include OnClickListener for Send Message button
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Note: We're not directly updating the text view, but calling setValue() to update the data in the database instead
                Message message = new Message(etMessageText.getText().toString(), etMessagePriority.getText().toString());
                messagePOJOReference.setValue(message);
            }
        });

    }
}