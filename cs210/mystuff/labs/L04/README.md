This lab helps you understand that bits can be used to represent all kinds of things. In this lab you will
explore strings and how they are stored in memory, you will also perform image file operations.

**stringbug**:  Your job is to fix a bug in a program that tries to concatenate two string together as much as possible in the space provided. See the file stringbug.c for more directions. 

**image**: For this one you need to write a program from scratch that reads in an image file in a specific
format perform a transformation on that image and write it back out to a file. The source file should be called image.c
The input image file is in a format called PPM and has the structure shown below.  PPM is one of many image file formats, we use it in this lab due to its very straightforward file format.

```
P6
width height
colors
<data for all the pixels>
```

The first three lines are all in ASCII, so you can read them with appropriate fscanf.  The data for the pixels is binary, thus not human readable, and you must use fread to read them into your program.
Each pixel has three colors and each color is one byte in size, so there are 3 * width * height bytes
to read for the image. 

You should use file input to read in the input image and file output two write a new image file in the same PPM format.
You can assume that width <= 1024 and height <= 1024 for this lab.

You are to transform the image by inverting (complementing) the bit values for each pixel.
You need to write out a valid PPM image file as your result. This file must have all the header lines
in ASCII and the correct binary data for the pixels.

Your program needs to take two command line arguments and needs to be called image.  You are provided
the PPM file sample.ppm  so an example program execuation would be:
`./image sample.ppm new.ppm`
this would execute your program and write a new file called new.ppm

You can view the input and output image files by opening them on your local machine.  For OIT containers use the scp command to copy the files.  The scp command is a remote copy utilty built that leverages ssh keys, so if your ssh
keys are configured properly this should work.

Example scp command: 
```scp -P 30002 term@cs-az-00.oit.duke.edu:~/210/labs/L04/new.ppm .```

You should replace the machine name _cs-az-00.oit.duke.edu_ with your OIT container hostname and new.ppm with whatever file you want to copy. I encourage you to look at the input sample.ppm and your output image to see the difference.

On a MAC you can use preview to view the new files, not sure what program on Windows.

You can test locally by runing the following on the unix command line
`python3 test_kit.py ALL`

You can also test image and stringbug independently using
`python3 test_kit.py stringbug`
`python3 test_kit.py image`

What to submit:
upload your completed stringbug.c and image.c to gradescope



