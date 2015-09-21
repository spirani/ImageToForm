# ImageToForm
A Java application to assist with filling out pre-printed forms

## Screenshot
![Screenshot](../blob/master/img/itf-scr.png?raw=true)

## Prerequisites
- Gradle is required to build this application.
- A recent version of the Java Development Kit should be installed.

## Building and Running

```
git clone https://github.com/spirani/ImageToForm.git
cd ImageToForm
gradle fullbuild
java -jar build/libs/ImageToForm-all-0.1.jar
```

## Usage
- To open a new scanned form, click `File -> Open Image`
- Clicking on a text box will select it.
  - A selected text box will be magenta, and an unselected text box will be red.
- Selecting a text box and middle-clicking anywhere in the image will move the selected text box to the cursor's location.
- Right-clicking on text box will allow the user to change its label.
- To add a new text box to the image, click `Add Text Box` and click on the location to add it to.
- To remove a text box, click `Remove Text Box` and click the text box to remove.
- To lengthen or shorten a text box, click `Increase Text Box Width` or `Decrease Text Box Width`
- To fill and export a form to PDF, click `File -> Export to PDF`
  - Click `Choose Save Location` to choose the pdf file's save location. The filename must end in `.pdf`.
  - Click `Export to PDF`
