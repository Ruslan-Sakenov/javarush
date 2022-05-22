package sakenov.cryptoanalyzer.commands;

import sakenov.cryptoanalyzer.constants.Constants;
import sakenov.cryptoanalyzer.entity.Result;
import sakenov.cryptoanalyzer.entity.ResultCode;

import java.io.*;
import java.util.ArrayList;

public class Decrypt implements Action {
    @Override
    public Result run(String[] parameters) {
        String inputFile = parameters[0];
        String outputFile = parameters[1];
        String encryptionKeyString = parameters[2];
        toDecrypt( inputFile, outputFile, encryptionKeyString );
        return  new Result( "Файл расшифрован", ResultCode.OK );
    }

    private void toDecrypt(String inputFile, String outputFile, String encryptionKeyString) {
        int encryptionKeyValue;
        encryptionKeyValue = Integer.parseInt( encryptionKeyString );
        try (BufferedReader bufferedReader = new BufferedReader( new FileReader( inputFile ) );
             BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter( outputFile ) )) {
            ArrayList<String> data = new ArrayList<>();
            while (bufferedReader.ready()) {
                String string = bufferedReader.readLine();
                char[] chars = string.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    int index = Constants.ALPHABET.indexOf( Character.toLowerCase( chars[i] ) );
                    if (index == -1) {
                        continue;
                    }

                    int shift;
                    if (encryptionKeyValue > 0) {
                        shift = (index - encryptionKeyValue) % Constants.ALPHABET.size();
                    } else shift = (index + encryptionKeyValue) % Constants.ALPHABET.size();
                    if (shift < 0) shift = shift + Constants.ALPHABET.size();
                    chars[i] = Constants.ALPHABET.get( shift );
                }
                data.add( new String( chars ) );
            }

            for (String string : data) {
                bufferedWriter.write( string + "\n" );
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
