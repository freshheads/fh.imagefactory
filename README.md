# fh.imagefactory Module

## Description

Freshheads ImageFactory module for Titanium which currently provides access to Androids ExifInterface for image files. Also provides a memory conservative utility method to rotate an image based on Exif information and resize it, maintaining aspect ratio, in one pass.

## Accessing the fh.imagefactory Module

To access this module from JavaScript, you would do the following:

    var ImageFactory = require("fh.imagefactory");

The ImageFactory variable is a reference to the Module object.	

## Methods

### ___PROJECTNAMEASIDENTIFIER__.getExifTag

Read the value of an Exif tag inside an image file.

#### Arguments

* filename [string]: The string specifying the filename of the image file to read.
* tag [string]: The tag to read from the image file. Use one of the constants also provided in this module. 

### ___PROJECTNAMEASIDENTIFIER__.rotateResizeImage

Rotate, resize and compress the image to a JPEG file in one pass. 

#### Arguments

* filename [string]: The string specifying the filename of the image file to modify. This file will be overwritten!
* size [int]: The size in pixels to resize to. This will be the size of the longest edge, remaining aspect ratio.
* quality [int]: The JPEG output quality specified as an integer between 0 and 100 with 100 being the highest quality.

## Constants

### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_FLIP_HORIZONTAL
### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_FLIP_VERTICAL
### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_NORMAL
### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_ROTATE_180
### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_ROTATE_270
### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_ROTATE_90
### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_TRANSPOSE
### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_TRANSVERSE
### ___PROJECTNAMEASIDENTIFIER__.ORIENTATION_UNDEFINED

### ___PROJECTNAMEASIDENTIFIER__.TAG_DATETIME
### ___PROJECTNAMEASIDENTIFIER__.TAG_FLASH
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_ALTITUDE
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_ALTITUDE_REF
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_DATESTAMP
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_LATITUDE
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_LATITUDE_REF
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_LONGITUDE
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_LONGITUDE_REF
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_PROCESSING_METHOD
### ___PROJECTNAMEASIDENTIFIER__.TAG_GPS_TIMESTAMP
### ___PROJECTNAMEASIDENTIFIER__.TAG_IMAGE_LENGTH
### ___PROJECTNAMEASIDENTIFIER__.TAG_IMAGE_WIDTH
### ___PROJECTNAMEASIDENTIFIER__.TAG_MAKE
### ___PROJECTNAMEASIDENTIFIER__.TAG_MODEL
### ___PROJECTNAMEASIDENTIFIER__.TAG_ORIENTATION
### ___PROJECTNAMEASIDENTIFIER__.TAG_WHITE_BALANCE

### ___PROJECTNAMEASIDENTIFIER__.WHITEBALANCE_AUTO
### ___PROJECTNAMEASIDENTIFIER__.WHITEBALANCE_MANUAL

## Usage

See example folder for an example app.js.

## Author

[Stefan Moonen] [1] - stefan.moonen@freshheads.com

## License

Copyright (c) 2013 by Freshheads BV. Please see the LICENSE file included in the distribution for further details.

  [1]: mailto:stefan.moonen@freshheads.com "Stefan Moonen"
  