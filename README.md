# fh.imagefactory Module

## Description

Freshheads ImageFactory module for Titanium which currently provides access to Androids ExifInterface for image files. Also provides a memory conservative utility method to rotate an image based on Exif information and resize it, maintaining aspect ratio, in one pass.

## Accessing the fh.imagefactory Module

To access this module from JavaScript, you would do the following:

    var ImageFactory = require("fh.imagefactory");

The ImageFactory variable is a reference to the Module object.	

## Methods

### ImageFactory.getExifTag

Read the value of an Exif tag inside an image file.

#### Arguments

* filename [string]: The string specifying the filename of the image file to read.
* tag [string]: The tag to read from the image file. Use one of the constants also provided in this module. 

### ImageFactory.rotateResizeImage

Rotate, resize and compress the image to a JPEG file in one pass. 

#### Arguments

* filename [string]: The string specifying the filename of the image file to modify. This file will be overwritten!
* size [int]: The size in pixels to resize to. This will be the size of the longest edge, remaining aspect ratio.
* quality [int]: The JPEG output quality specified as an integer between 0 and 100 with 100 being the highest quality.

## Constants

### ImageFactory.ORIENTATION_FLIP_HORIZONTAL
### ImageFactory.ORIENTATION_FLIP_VERTICAL
### ImageFactory.ORIENTATION_NORMAL
### ImageFactory.ORIENTATION_ROTATE_180
### ImageFactory.ORIENTATION_ROTATE_270
### ImageFactory.ORIENTATION_ROTATE_90
### ImageFactory.ORIENTATION_TRANSPOSE
### ImageFactory.ORIENTATION_TRANSVERSE
### ImageFactory.ORIENTATION_UNDEFINED

### ImageFactory.TAG_DATETIME
### ImageFactory.TAG_FLASH
### ImageFactory.TAG_GPS_ALTITUDE
### ImageFactory.TAG_GPS_ALTITUDE_REF
### ImageFactory.TAG_GPS_DATESTAMP
### ImageFactory.TAG_GPS_LATITUDE
### ImageFactory.TAG_GPS_LATITUDE_REF
### ImageFactory.TAG_GPS_LONGITUDE
### ImageFactory.TAG_GPS_LONGITUDE_REF
### ImageFactory.TAG_GPS_PROCESSING_METHOD
### ImageFactory.TAG_GPS_TIMESTAMP
### ImageFactory.TAG_IMAGE_LENGTH
### ImageFactory.TAG_IMAGE_WIDTH
### ImageFactory.TAG_MAKE
### ImageFactory.TAG_MODEL
### ImageFactory.TAG_ORIENTATION
### ImageFactory.TAG_WHITE_BALANCE

### ImageFactory.WHITEBALANCE_AUTO
### ImageFactory.WHITEBALANCE_MANUAL

## Usage

See example folder for an example app.js.

## Author

[Stefan Moonen] [1]

## License

Copyright (c) 2013 by Freshheads BV. Please see the LICENSE file included in the distribution for further details.

  [1]: mailto:stefan.moonen@freshheads.com "Stefan Moonen"
  
