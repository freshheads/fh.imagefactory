//
// Load the module
//
var ImageFactory = require('fh.imagefactory');


//
// The list of all currently supported Exif tags (API level <= 10)
//
var exifTags = {
  'Date/time': ImageFactory.TAG_DATETIME,
  'Flash': ImageFactory.TAG_FLASH,
  'GPS altitude': ImageFactory.TAG_GPS_ALTITUDE,
  'GPS altitude ref': ImageFactory.TAG_GPS_ALTITUDE_REF,
  'GPS date stamp': ImageFactory.TAG_GPS_DATESTAMP,
  'GPS latitude': ImageFactory.TAG_GPS_LATITUDE,
  'GPS latitude ref': ImageFactory.TAG_GPS_LATITUDE_REF,
  'GPS longitude': ImageFactory.TAG_GPS_LONGITUDE,
  'GPS longitude ref': ImageFactory.TAG_GPS_LONGITUDE_REF,
  'GPS processing method': ImageFactory.TAG_GPS_PROCESSING_METHOD,
  'GPS timestamp': ImageFactory.TAG_GPS_TIMESTAMP,
  'Image length': ImageFactory.TAG_IMAGE_LENGTH,
  'Image width': ImageFactory.TAG_IMAGE_WIDTH,
  'Camera make': ImageFactory.TAG_MAKE,
  'Camera model': ImageFactory.TAG_MODEL,
  'Orientation': ImageFactory.TAG_ORIENTATION,
  'White balance': ImageFactory.TAG_WHITEBALANCE
};

// 
// Create a basic interface
//
var win = Ti.UI.createWindow({
  backgroundColor: 'white'
});

var scrollView = Ti.UI.createScrollView({
  height: Ti.UI.FILL,
  layout: 'vertical',
  width: Ti.UI.FILL
});
win.add(scrollView);

var buttonView = Ti.UI.createView({
  height: Ti.UI.SIZE,
  top: '20dip',
  width: '260dip'
});
scrollView.add(buttonView);

var cameraButton = Ti.UI.createButton({
  left: 0,
  title: 'Open camera',
  width: '120dip'
});
cameraButton.addEventListener('click', openCamera);
buttonView.add(cameraButton);

var galleryButton = Ti.UI.createButton({
  right: 0,
  title: 'Open gallery',
  width: '120dip'
});
galleryButton.addEventListener('click', openGalery);
buttonView.add(galleryButton);

var previewImage = Ti.UI.createImageView({
  left: '20dip',
  right: '20dip',
  top: '20dip'
});
scrollView.add(previewImage);


/**
 * Button event to open the camera
 * 
 * @param {Object} e
 */
function openCamera(e) {
  Ti.Media.showCamera({
    mediaTypes: [ Ti.Media.MEDIA_TYPE_PHOTO ],
    saveToPhotoGallery: false,
    success: captureSuccess
  });
};


/**
 * Button event to open the photo gallery
 * 
 * @param {Object} e
 */
function openGalery(e) {
  Ti.Media.openPhotoGallery({
    mediaTypes: [ Ti.Media.MEDIA_TYPE_PHOTO ],
    success: captureSuccess
  });
};


/**
 * Capture event
 * 
 * @param {Object} e
 */
function captureSuccess(e) {
  var nativePath = e.media.nativePath;
  
  // Read exif information before rotating and resizing the image, because 
  // data is not conserved after altering the image due to performance
  var exifInformation = 'Exif information:' + "\n";
  for (tag in exifTags)
  {
    exifInformation += "\n" + tag + ': ' + ImageFactory.getExifTag(nativePath, exifTags[tag]);
  }
  
  var maximumSize = 800;
  var jpegQuality = 70;
  
  ImageFactory.rotateResizeImage(nativePath, maximumSize, jpegQuality);
  
  previewImage.image = nativePath;
  
  alert(exifInformation);
};


//
// Open the window
//
win.open();
