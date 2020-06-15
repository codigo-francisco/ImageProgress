# ImageProgress
ImageProgress is an android library to show the loading progress in an ImageView painting a part of the image in original color an the rest in black and white.

ImageView with 15% of progress  
<img src="screenshots/first.png" width="50%" height="600" />

Same image with 75% of progress  
<img src="screenshots/second.png" width="50%" height="600" />

## Instalation
You need to add this line in your build.gradle

implementation 'io.github.rockbass2560:imageprogress:1.0.0'

## Example of use

You can add the view in your layout.xml, the attribute value needs to be a value between 0 and 100 (percent value of the image view)

![First Example](screenshots/first_example.png)

To change the value you can modify value property and the image will paint color part automatically

![Second Example](screenshots/second_example.png)
