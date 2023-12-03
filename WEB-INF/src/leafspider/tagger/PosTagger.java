package leafspider.tagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import leafspider.util.Log;
import leafspider.util.Util;


public class PosTagger 
{
    private static String exePath = "C:\\TreeTagger\\bin\\tag-english.bat"; //tag.bat";
    private static String tmpPath = "C:\\Server\\tomcat6\\webapps\\soft\\data\\stores\\tmp"; //tag.bat";
    public List<String> lines;
    public List<String> nouns;
    public List<String> verbs;
    
    public static void main(String[] args)
    {
        try
        {
            PosTagger tagger = new PosTagger();
            tagger.tagFile("C:\\Catalog\\news0\\export1.txt");
//            tagger.tagFile("C:\\Server\\tomcat6\\webapps\\soft\\data\\stores\\tmp\\deal_0.0599315248186888.txt");
            
            for (Iterator it = tagger.nouns.iterator(); it.hasNext();)
            {
            	String word = (String) it.next();
                System.out.println("" + word);
            }
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
    }
    
    public void tagString(String st)
    {
    	File file = new File(tmpPath + "\\deal_" + Math.random() + ".txt");
    	file.getParentFile().mkdirs();
    	Util.writeAsFile(st, file.getAbsolutePath());
    	tagFile(file.getAbsolutePath());
    	file.delete();
    }    

    public void tagFile(String filePath)
    {
    	String[] cmdList = { exePath, filePath };		
		Process proc = null;
		try 
		{ 
			proc = Runtime.getRuntime().exec(cmdList);
		
			BufferedReader commandResult = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			List list = new ArrayList();
			String st = null;
			while ((st = commandResult.readLine()) != null)
			{
				list.add(st); // Ignore read errors; they mean process is done
			}
			commandResult.close();
			
			String[] array = (String[]) list.toArray(new String[list.size()]);
			
	//        String[] lines = result.Split(new string[] { "\n", "\r\n" }, StringSplitOptions.RemoveEmptyEntries);
	
	        lines = new ArrayList<String>();
	        nouns = new ArrayList<String>();
	        verbs = new ArrayList<String>();
	
	        for (int i = 0; i < array.length; i++)
	        {
	        	lines.add(array[i]);
//	        	System.out.println(array[i]);

//            	String[] vals = lines[i].Split(new string[] { "\t" }, StringSplitOptions.RemoveEmptyEntries);
	            String[] vals = array[i].split("\t");
	            if (vals.length > 1)
	            {
	                if (vals[1].indexOf("N") == 0)
	                {
//	                    nouns.add(vals[0].toLowerCase());
	                    nouns.add(vals[2]);
	                }
	                else if (vals[1].indexOf("V") == 0)
	                {
//	                    verbs.add(vals[0].toLowerCase());
	                    verbs.add(vals[2]);
	                }
	                else
	                {
//                      	System.out.println(vals[1] + "=" + vals[2]);
	                }
	            }
	        }
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			proc.destroy();
		}
    }
    
    /* Parts of Speech: The Penn Treebank Project
    CC Coordinating conjunction
    CD Cardinal number
    DT Determiner
    EX Existential there
    FW Foreign word
    IN Preposition or subordinating conjunction
    JJ Adjective
    JJR Adjective, comparative
    JJS Adjective, superlative
    LS List item marker
    MD Modal
    NN Noun, singular or mass
    NNS Noun, plural
    NNP Proper noun, singular
    NNPS Proper noun, plural
    PDT Predeterminer
    POS Possessive ending
    PRP Personal pronoun
    PRP$ Possessive pronoun
    RB Adverb
    RBR Adverb, comparative
    RBS Adverb, superlative
    RP Particle
    SYM Symbol
    TO to
    UH Interjection
    VB Verb, base form
    VBD Verb, past tense
    VBG Verb, gerund or present participle
    VBN Verb, past participle
    VBP Verb, non­3rd person singular present
    VBZ Verb, 3rd person singular present
    WDT Wh­determiner
    WP Wh­pronoun
    WP$ Possessive wh­pronoun
    WRB Wh­adverb
    */
 
}
