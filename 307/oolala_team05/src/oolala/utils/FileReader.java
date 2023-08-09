package oolala.utils;

import javafx.stage.FileChooser;
import oolala.view.Turtle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


public class FileReader {

  /**
   * prompts user to pick a file and sends it off to be read
   */
  FileChooser fileChooser;

  public FileReader() {
    fileChooser = new FileChooser();
  }

  public void addToQueue(Queue<String> instructionQueue) {
    /**
     * reads a file and adds the commands to a queue
     * If tell is used, multiple of the same command is added so all told-turtles are accounted for
     */
    List<String> lines = selectFile();
    int numTurtles=0;
    boolean addMore = false;
    for (int idx = 0; idx <= lines.size() - 1; idx++) {
      if(lines.get(idx).startsWith("tell")) {
        String[] tellSplit = lines.get(idx).split("\\s+");
        if (tellSplit.length-1 > 1) {addMore = true;}
        else {addMore = false;}
        numTurtles = tellSplit.length - 1;
      }
      if (addMore) {
        for (int i=0; i<numTurtles; i++) {
          instructionQueue.add(lines.get(idx));
        }
      }
      else {
        instructionQueue.add(lines.get(idx));
      }
    }
  }

  public List<String> selectFile() {
    /**
     * opens a file window to allow the user to select a file
     * selected file is sent through readfile
     * return an arraylist where each entry is one command
     */
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    File selectedFile = fileChooser.showOpenDialog(null); //need to look into this

    return readFile(selectedFile);
  }

  public List<String> readFile(File filename) {
    /**
     * reads each line in the given file
     * return an arraylist where each entry is one command
     */
    ArrayList<String> ret = new ArrayList<>();
    try {
      Scanner sc = new Scanner(filename);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        line = line.trim();
        if (line == "" || line.charAt(0) == '#') {
          continue;
        }
        ArrayList<String> commands = getAllCommandsInLine(line);
        ret.addAll(commands);
      }
      sc.close();
    } catch (Exception e) {
      System.out.println(e.toString());
      System.out.println("Bad File");
    }
    return ret;
  }

  private ArrayList<String> getAllCommandsInLine(String line) {
    /**
     * handles when multiple commands are on 1 line
     */
    String str = line;
    ArrayList<String> ret = new ArrayList<>();
    String[] allCommands = str.split("(?<=[0-9] )");
    if (str.startsWith("tell")) {
      allCommands = new String[]{str};
    }
    for (int i = 0; i < allCommands.length; i++) {
      ret.add(allCommands[i].trim());
    }

    return ret;
  }
}
