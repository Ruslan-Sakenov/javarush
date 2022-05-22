package sakenov.cryptoanalyzer.commands;

import sakenov.cryptoanalyzer.constants.Constants;
import sakenov.cryptoanalyzer.entity.Result;
import sakenov.cryptoanalyzer.entity.ResultCode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BruteForce implements Action{
    public Result run(String[] parameters) {
        String inputFile = parameters[0];
        String outputFile = parameters[1];
        toBruteforce( inputFile, outputFile );
        return new Result( "файл расшифрован грубой силой", ResultCode.OK );
    }

    private void toBruteforce(String inputFile, String outputFile) {
        try (BufferedReader bufferedReader = new BufferedReader( new FileReader( inputFile ) );
             BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter( outputFile ) )) {
            ArrayList<String> inputData = new ArrayList<>();
            ArrayList<String> outputData = new ArrayList<>();

            while (bufferedReader.ready()) {
                inputData.add( bufferedReader.readLine() );
            }
            for (int key = 1; key < Constants.ALPHABET.size(); key++) {
                for (String string : inputData) {
                    char[] chars = string.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        int index = Constants.ALPHABET.indexOf( Character.toLowerCase( chars[i] ) );
                        if (index == -1) {
                            continue;
                        }

                        int shift = (index - key) % Constants.ALPHABET.size();
                        if (shift < 0) shift = shift + Constants.ALPHABET.size();
                        chars[i] = Constants.ALPHABET.get( shift );
                    }
                    outputData.add( new String( chars ) );
                }

                boolean isCorrectLength = true;
                boolean isCorrectWord;
                int notCorrectWord = 0;
                int countWords = 0;

                for (String string : outputData) {
                    if (string.matches( "(.*)[a-zA-Z](.*)" )) {
                        continue;
                    }

                    String[] stringsLength = string.split( " " );
                    for (String s : stringsLength) {
                        if (s.length() > 25) {
                            isCorrectLength = false;
                            break;
                        }
                    }
                    String[] stringsWord = string.split( "[?!.]" );
                    for (String s : stringsWord) {
                        if (stringsWord.length == 1 | s.length() == 1 | s.isEmpty()) {
                            break;
                        }
                        if (!s.startsWith( " " )) {
                            notCorrectWord++;
                        }
                    }
                }

                for (String string : outputData) {
                    String[] words = string.split( " " );
                    countWords += words.length;
                }
                isCorrectWord = notCorrectWord <= countWords / 10;
                if (isCorrectLength & isCorrectWord) {
                    System.out.println( "ключ подобран - " + key );
                    break;
                }
                outputData.clear();
            }
            for (String string : outputData) {
                bufferedWriter.write( string + "\n" );
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
