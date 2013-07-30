package fh.imagefactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.util.TiConvert;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

@Kroll.module(name="Imagefactory", id="fh.imagefactory")
public class ImagefactoryModule extends KrollModule
{

	// Standard Debugging variables
	private static final String TAG = "ImagefactoryModule";
	
	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;
	@Kroll.constant public static final int ORIENTATION_FLIP_HORIZONTAL = ExifInterface.ORIENTATION_FLIP_HORIZONTAL;
	@Kroll.constant public static final int ORIENTATION_FLIP_VERTICAL = ExifInterface.ORIENTATION_FLIP_VERTICAL;
	@Kroll.constant public static final int ORIENTATION_NORMAL = ExifInterface.ORIENTATION_NORMAL;
	@Kroll.constant public static final int ORIENTATION_ROTATE_180 = ExifInterface.ORIENTATION_ROTATE_180;
	@Kroll.constant public static final int ORIENTATION_ROTATE_270 = ExifInterface.ORIENTATION_ROTATE_270;
	@Kroll.constant public static final int ORIENTATION_ROTATE_90 = ExifInterface.ORIENTATION_ROTATE_90;
	@Kroll.constant public static final int ORIENTATION_TRANSPOSE = ExifInterface.ORIENTATION_TRANSPOSE;
	@Kroll.constant public static final int ORIENTATION_TRANSVERSE = ExifInterface.ORIENTATION_TRANSVERSE;
	@Kroll.constant public static final int ORIENTATION_UNDEFINED = ExifInterface.ORIENTATION_UNDEFINED;

	@Kroll.constant public static final String TAG_DATETIME = ExifInterface.TAG_DATETIME;
	@Kroll.constant public static final String TAG_FLASH = ExifInterface.TAG_FLASH;
	@Kroll.constant public static final String TAG_GPS_ALTITUDE = ExifInterface.TAG_GPS_ALTITUDE;
	@Kroll.constant public static final String TAG_GPS_ALTITUDE_REF = ExifInterface.TAG_GPS_ALTITUDE_REF;
	@Kroll.constant public static final String TAG_GPS_DATESTAMP = ExifInterface.TAG_GPS_DATESTAMP;
	@Kroll.constant public static final String TAG_GPS_LATITUDE = ExifInterface.TAG_GPS_LATITUDE;
	@Kroll.constant public static final String TAG_GPS_LATITUDE_REF = ExifInterface.TAG_GPS_LATITUDE_REF;
	@Kroll.constant public static final String TAG_GPS_LONGITUDE = ExifInterface.TAG_GPS_LONGITUDE;
	@Kroll.constant public static final String TAG_GPS_LONGITUDE_REF = ExifInterface.TAG_GPS_LONGITUDE_REF;
	@Kroll.constant public static final String TAG_GPS_PROCESSING_METHOD = ExifInterface.TAG_GPS_PROCESSING_METHOD;
	@Kroll.constant public static final String TAG_GPS_TIMESTAMP = ExifInterface.TAG_GPS_TIMESTAMP;
	@Kroll.constant public static final String TAG_IMAGE_LENGTH = ExifInterface.TAG_IMAGE_LENGTH;
	@Kroll.constant public static final String TAG_IMAGE_WIDTH = ExifInterface.TAG_IMAGE_WIDTH;
	@Kroll.constant public static final String TAG_MAKE = ExifInterface.TAG_MAKE;
	@Kroll.constant public static final String TAG_MODEL = ExifInterface.TAG_MODEL;
	@Kroll.constant public static final String TAG_ORIENTATION = ExifInterface.TAG_ORIENTATION;
	@Kroll.constant public static final String TAG_WHITE_BALANCE = ExifInterface.TAG_WHITE_BALANCE;

	@Kroll.constant public static final int WHITEBALANCE_AUTO = ExifInterface.WHITEBALANCE_AUTO;
	@Kroll.constant public static final int WHITEBALANCE_MANUAL = ExifInterface.WHITEBALANCE_MANUAL;
	
	
	public ImagefactoryModule()
	{
		super();
	}
	
	private String convertPath(String path)
	{
		Log.i(TAG, "Open FileInputStream for path: " + path);
		
		if (path.startsWith("file://") || path.startsWith("content://") || path.startsWith("appdata://") || path.startsWith("appdata-private://"))
		{
			path = path.replaceAll("file://", "");
			path = path.replaceAll("content://", "");
        	path = path.replaceAll("appdata:///?", "/mnt/sdcard/" + TiApplication.getInstance().getPackageName() + "/");
        	path = path.replaceAll("appdata-private:///?", "/data/data/" + TiApplication.getInstance().getPackageName() + "/app_appdata/");

        	Log.i(TAG, "Converted path to: " + path);
		}
		
		return path;
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		// Module initialization
	}

	// Methods
	@Kroll.method
	public String getExifTag(String filename, String tag)
	{
		String property = "";
		ExifInterface exif;
		
		filename = convertPath(filename);
		
		try {
			Log.i(TAG, "Read Exif tag: " + tag + ", from file: " + filename);
			
			exif = new ExifInterface(filename);			
			property = exif.getAttribute(tag);
			
			Log.i(TAG, "Exif tag value: " + property);
		}
		catch (IOException e)
		{
			property = ""; 

			Log.e(TAG, "IO Exception occured, file probably does not exist.");
		}
				
		return property;
	}
	
	/**
	 * Auto rotate and resize to the desired maximum size keeping aspect
	 * 
	 * @param imageBlob
	 * @param size
	 * @param compression
	 * @return
	 */
	@Kroll.method
	public Boolean rotateResizeImage(String filename, int size, int quality)
	{
		try
		{
			// Determine the orientation
			int orientation = TiConvert.toInt(getExifTag(filename, TAG_ORIENTATION));
			Log.i(TAG, "Detected orientation: " + orientation);
			
			int rotation = 0;
			switch (orientation)
			{
				case ORIENTATION_ROTATE_90:
					rotation = 90;
					break;
				
				case ORIENTATION_ROTATE_180:
					rotation = 180;
					break;
				
				case ORIENTATION_ROTATE_270:
					rotation = 270;
					break;
			}
			
			Log.i(TAG, "Determined rotation: " + rotation);			
			
			// Read the file path into a File
			File imageFile = new File(convertPath(filename));
		    
			// Decode once to determine size
			BitmapFactory.Options opts = new BitmapFactory.Options();
	        opts.inJustDecodeBounds = true;

	        BitmapFactory.decodeStream(new FileInputStream(imageFile), null, opts);

			Log.i(TAG, "Original image size: " + opts.outWidth + "x" + opts.outHeight);			

	        // Determine scaling based on size
			int sample = 1;			
			while (opts.outWidth / sample / 2 >= size || opts.outHeight / sample / 2 >= size)
				sample *= 2;
			
			opts = new BitmapFactory.Options();
			opts.inSampleSize = sample;

			Log.i(TAG, "Sample size: " + sample);			
			
			// Decode with scaling applied to save a huge amount of memory
	        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile), null, opts);

			Log.i(TAG, "Sampled image size: " + opts.outWidth + "x" + opts.outHeight);			
	        
	        // Determine the correct scale
	        float scale = (float)size / (float)(opts.outWidth > opts.outHeight ? opts.outWidth : opts.outHeight);
	        
	        // Create the appropriate Matrix to resize and rotate
			Matrix matrix = new Matrix();
			matrix.postRotate(rotation);
			matrix.postScale(scale, scale);
			
			Bitmap image = Bitmap.createBitmap(bitmap, 0, 0, (int)bitmap.getWidth(), (int)bitmap.getHeight(), matrix, true);

			Log.i(TAG, "Scaled image size: " + image.getWidth() + "x" + image.getHeight());			
						
	        // Delete old image
			imageFile.delete();
	        
	        // Create new image
			imageFile.createNewFile();
	        
	        FileOutputStream fOut = new FileOutputStream(imageFile);
	        image.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
	        fOut.flush();
	        fOut.close();

			return true;
		}
		catch (Exception e)
		{
			for (StackTraceElement elm : e.getStackTrace())
			{
				Log.e(TAG, elm.toString());
			}
			
			return false;
		}
	}
}

