package com.example.benvc.films;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.security.AccessController.getContext;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    TextView txtTitle;
    TextView txtRating;
    TextView txtDesc;
    ImageView imgThumb;
    Button btnNext;
    Button btnPrevious;
    Button btnTrailer;
    RadioButton OneStar;
    RadioButton TwoStar;
    RadioButton ThreeStar;
    RadioButton FourStar;
    RadioButton FiveStar;
    Button btnDelete;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        imgThumb = (ImageView) findViewById(R.id.imgThumb);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnTrailer = (Button) findViewById(R.id.btnTrailer);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        OneStar = (RadioButton) findViewById(R.id.OneStar);
        TwoStar = (RadioButton) findViewById(R.id.TwoStar);
        ThreeStar = (RadioButton) findViewById(R.id.ThreeStar);
        FourStar = (RadioButton) findViewById(R.id.FourStar);
        FiveStar = (RadioButton) findViewById(R.id.FiveStar);
        //Instancing
        DbHelper mDbHelper = new DbHelper(this.getBaseContext());
        Seeds seed = new Seeds();
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //Delete then recreate the database
        db.execSQL(DbHelper.SQL_DELETE_ENTRIES);

        try {
            db.execSQL(DbHelper.SQL_CREATE_ENTRIES);
            db.execSQL(seed.GuardiansOfTheGalaxy);
            db.execSQL(seed.Fury);
            //db.execSQL(seed.IngloriousBastards);
            db.execSQL(seed.Fast);
            db.execSQL(seed.BatmanvSuperman);
            db.execSQL(seed.CaptainAmerica);
            db.execSQL(seed.Avengers);
        } catch (Exception e) {
            e.printStackTrace();
        }
            //Seed the database

            String select = "SELECT * FROM films;";
            final Cursor result = db.rawQuery(select, null);
            result.moveToFirst();
            final Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                    result.getString(4), result.getString(5));
            //Get rating
            int rating = film.getRating();
            if (rating == 1) {
                OneStar.toggle();
            } else if (rating == 2) {
                TwoStar.toggle();
            } else if (rating == 3) {
                ThreeStar.toggle();
            } else if (rating == 4) {
                FourStar.toggle();
            } else if (rating == 5) {
                FiveStar.toggle();
            }
            txtTitle.setText(film.getTitle());
            txtRating.setText(" Rating:" + film.getRating());
            txtDesc.setText("Description: \n" + film.getDesc());
            new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
            OnClickListener oclDelete = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    result.moveToPosition(count);
                    Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                            result.getString(4), result.getString(5));
                    String KEY_ID = "id";
                    db.delete("films",KEY_ID + "=" + film.getId(),null);
                    String select = "SELECT * FROM films;";
                    final Cursor result = db.rawQuery(select, null);
                    result.moveToFirst();
                    film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                            result.getString(4), result.getString(5));
                    //Get rating
                    int rating = film.getRating();
                    if (rating == 1) {
                        OneStar.toggle();
                    } else if (rating == 2) {
                        TwoStar.toggle();
                    } else if (rating == 3) {
                        ThreeStar.toggle();
                    } else if (rating == 4) {
                        FourStar.toggle();
                    } else if (rating == 5) {
                        FiveStar.toggle();
                    }
                    txtTitle.setText(film.getTitle());
                    txtRating.setText(" Rating:" + film.getRating());
                    txtDesc.setText("Description: \n" + film.getDesc());
                    new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
                }
            };
            OnClickListener oclOneStar = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        result.moveToPosition(count);
                        Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        film.setRating(1);
                        ContentValues values = new ContentValues();
                        values.put("rating", film.getRating());
                        String KEY_ID = "id";
                        int rowsUpdated = db.update("films", values, KEY_ID + "=" + film.getId(), null);
                        String select = "SELECT * FROM films;";
                        final Cursor result = db.rawQuery(select, null);
                        result.moveToPosition(count);
                        film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        int rating = film.getRating();
                        if (rating == 1) {
                            OneStar.toggle();
                        } else if (rating == 2) {
                            TwoStar.toggle();
                        } else if (rating == 3) {
                            ThreeStar.toggle();
                        } else if (rating == 4) {
                            FourStar.toggle();
                        } else if (rating == 5) {
                            FiveStar.toggle();
                        }
                        txtTitle.setText(film.getTitle());
                        txtRating.setText(" Rating:" + film.getRating());
                        txtDesc.setText("Description: \n" + film.getDesc());
                        new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
                    } catch (Exception e) {
                    }
                }
            };
            OnClickListener oclTwoStar = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        result.moveToPosition(count);
                        Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        film.setRating(2);
                        ContentValues values = new ContentValues();
                        values.put("rating", film.getRating());
                        String KEY_ID = "id";
                        int rowsUpdated = db.update("films", values, KEY_ID + "=" + film.getId(), null);
                        String select = "SELECT * FROM films;";
                        final Cursor result = db.rawQuery(select, null);
                        result.moveToPosition(count);
                        film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        int rating = film.getRating();
                        if (rating == 1) {
                            OneStar.toggle();
                        } else if (rating == 2) {
                            TwoStar.toggle();
                        } else if (rating == 3) {
                            ThreeStar.toggle();
                        } else if (rating == 4) {
                            FourStar.toggle();
                        } else if (rating == 5) {
                            FiveStar.toggle();
                        }
                        txtTitle.setText(film.getTitle());
                        txtRating.setText(" Rating:" + film.getRating());
                        txtDesc.setText("Description: \n" + film.getDesc());
                        new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
                    } catch (Exception e) {
                    }
                }
            };
            OnClickListener oclThreeStar = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        result.moveToPosition(count);
                        Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        film.setRating(3);
                        ContentValues values = new ContentValues();
                        values.put("rating", film.getRating());
                        String KEY_ID = "id";
                        int rowsUpdated = db.update("films", values, KEY_ID + "=" + film.getId(), null);
                        String select = "SELECT * FROM films;";
                        final Cursor result = db.rawQuery(select, null);
                        result.moveToPosition(count);
                        film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        int rating = film.getRating();
                        if (rating == 1) {
                            OneStar.toggle();
                        } else if (rating == 2) {
                            TwoStar.toggle();
                        } else if (rating == 3) {
                            ThreeStar.toggle();
                        } else if (rating == 4) {
                            FourStar.toggle();
                        } else if (rating == 5) {
                            FiveStar.toggle();
                        }
                        txtTitle.setText(film.getTitle());
                        txtRating.setText(" Rating:" + film.getRating());
                        txtDesc.setText("Description: \n" + film.getDesc());
                        new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
                    } catch (Exception e) {
                    }
                }
            };
            OnClickListener oclFourStar = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        result.moveToPosition(count);
                        Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        film.setRating(4);
                        ContentValues values = new ContentValues();
                        values.put("rating", film.getRating());
                        String KEY_ID = "id";
                        int rowsUpdated = db.update("films", values, KEY_ID + "=" + film.getId(), null);
                        String select = "SELECT * FROM films;";
                        final Cursor result = db.rawQuery(select, null);
                        result.moveToPosition(count);
                        film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        int rating = film.getRating();
                        if (rating == 1) {
                            OneStar.toggle();
                        } else if (rating == 2) {
                            TwoStar.toggle();
                        } else if (rating == 3) {
                            ThreeStar.toggle();
                        } else if (rating == 4) {
                            FourStar.toggle();
                        } else if (rating == 5) {
                            FiveStar.toggle();
                        }
                        txtTitle.setText(film.getTitle());
                        txtRating.setText(" Rating:" + film.getRating());
                        txtDesc.setText("Description: \n" + film.getDesc());
                        new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
                    } catch (Exception e) {
                    }
                }
            };
            OnClickListener oclFiveStar = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        result.moveToPosition(count);
                        Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        film.setRating(5);
                        ContentValues values = new ContentValues();
                        values.put("rating", film.getRating());
                        String KEY_ID = "id";
                        int rowsUpdated = db.update("films", values, KEY_ID + "=" + film.getId(), null);
                        int i = rowsUpdated;
                        String select = "SELECT * FROM films;";
                        final Cursor result = db.rawQuery(select, null);
                        result.moveToPosition(count);
                        film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        int rating = film.getRating();
                        if (rating == 1) {
                            OneStar.toggle();
                        } else if (rating == 2) {
                            TwoStar.toggle();
                        } else if (rating == 3) {
                            ThreeStar.toggle();
                        } else if (rating == 4) {
                            FourStar.toggle();
                        } else if (rating == 5) {
                            FiveStar.toggle();
                        }
                        txtTitle.setText(film.getTitle());
                        txtRating.setText(" Rating:" + film.getRating());
                        txtDesc.setText("Description: \n" + film.getDesc());
                        new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
                    } catch (Exception e) {
                    }
                }
            };

            OnClickListener oclNext = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        count += 1;
                        String select = "SELECT * FROM films;";
                        final Cursor result = db.rawQuery(select, null);
                        result.moveToPosition(count);
                        Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        int rating = film.getRating();
                        if (rating == 1) {
                            OneStar.toggle();
                        } else if (rating == 2) {
                            TwoStar.toggle();
                        } else if (rating == 3) {
                            ThreeStar.toggle();
                        } else if (rating == 4) {
                            FourStar.toggle();
                        } else if (rating == 5) {
                            FiveStar.toggle();
                        }
                        txtTitle.setText(film.getTitle());
                        txtRating.setText(" Rating:" + film.getRating());
                        txtDesc.setText("Description: \n" + film.getDesc());
                        new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
                    } catch (Exception e) {
                    }

                }
            };
            OnClickListener oclPrevious = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        count -= 1;
                        String select = "SELECT * FROM films;";
                        final Cursor result = db.rawQuery(select, null);
                        result.moveToPosition(count);

                        Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));
                        //Get rating
                        int rating = film.getRating();
                        if (rating == 1) {
                            OneStar.toggle();
                        } else if (rating == 2) {
                            TwoStar.toggle();
                        } else if (rating == 3) {
                            ThreeStar.toggle();
                        } else if (rating == 4) {
                            FourStar.toggle();
                        } else if (rating == 5) {
                            FiveStar.toggle();
                        }
                        txtTitle.setText(film.getTitle());
                        txtRating.setText(" Rating:" + film.getRating());
                        txtDesc.setText("Description: \n" + film.getDesc());
                        new ImageLoadTask(film.getThumbnail(), imgThumb).execute();
                    } catch (Exception e) {
                    }
                }
            };
            OnClickListener oclTrailer = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        result.moveToPosition(count);
                        Film film = new Film(result.getString(0), result.getString(1), result.getString(2), result.getString(3),
                                result.getString(4), result.getString(5));

                        Intent intent = new Intent(MainActivity.this, TrailerActivity.class);
                        String URL = film.getTrailer();
                        intent.putExtra("VIDEO_URL", URL);
                        startActivity(intent);
                    }catch(Exception e){}
                }
            };
            btnPrevious.setOnClickListener(oclPrevious);
            btnNext.setOnClickListener(oclNext);
            btnTrailer.setOnClickListener(oclTrailer);
            OneStar.setOnClickListener(oclOneStar);
            TwoStar.setOnClickListener(oclTwoStar);
            ThreeStar.setOnClickListener(oclThreeStar);
            FourStar.setOnClickListener(oclFourStar);
            FiveStar.setOnClickListener(oclFiveStar);
            btnDelete.setOnClickListener(oclDelete);

        }


    }

