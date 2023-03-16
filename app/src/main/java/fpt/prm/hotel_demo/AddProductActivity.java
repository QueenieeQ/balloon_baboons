package fpt.prm.hotel_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText productQualityEditText;
    private EditText productDateEditText;
    private Button add_product_button;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_layout);

        // Get references to the views
        productNameEditText = findViewById(R.id.product_name_edittext);
        productPriceEditText = findViewById(R.id.product_price_edittext);
        productQualityEditText = findViewById(R.id.product_quality_edittext);
        productDateEditText = findViewById(R.id.product_date_edittext);
        add_product_button = findViewById(R.id.add_product_button);

        // Open the database
        MyDatabaseHelper helper = new MyDatabaseHelper(this);
        database = helper.getWritableDatabase();

        // Set a click listener on the add button
        add_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the EditTexts
                String name = productNameEditText.getText().toString();
                double price = Double.parseDouble(productPriceEditText.getText().toString());
                int quality = Integer.parseInt(productQualityEditText.getText().toString());
                String date = productDateEditText.getText().toString();

                // Create a ContentValues object to hold the data
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("price", price);
                values.put("quality", quality);
                values.put("date", date);

                // Insert the data into the database
                database.insert("products", null, values);

                // Show a toast message to indicate success
                Toast.makeText(AddProductActivity.this, "Product added", Toast.LENGTH_SHORT).show();

                // Clear the EditTexts
                productNameEditText.setText("");
                productPriceEditText.setText("");
                productQualityEditText.setText("");
                productDateEditText.setText("");
            }
        });
    }

    // Create a SQLiteOpenHelper class to manage your database
    private static class MyDatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "products.db";
        private static final int DATABASE_VERSION = 1;

        public MyDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create the table if it doesn't exist
            db.execSQL("CREATE TABLE IF NOT EXISTS products " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price REAL, quality INTEGER, date TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop the table if it exists and create a new one
            db.execSQL("DROP TABLE IF EXISTS products");
            onCreate(db);
        }
    }
}

