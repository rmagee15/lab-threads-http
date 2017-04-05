import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * A class for downloading movie data from the internet.
 * Code adapted from Google.
 *
 * YOUR TASK: Add comments explaining how this code works!
 * 
 * @author Joel Ross & Kyungmin Lee
 */
public class MovieDownloader {

	//handles getting the JSON formatted information from the api about a movie based 
	//on the the String parameter passed in by the user
	public static String[] downloadMovieData(String movie) {

		//construct the url for the omdbapi API
		String urlString = "";
		try {
			urlString = "http://www.omdbapi.com/?s=" + URLEncoder.encode(movie, "UTF-8") + "&type=movie";
		}catch(UnsupportedEncodingException uee){
			return null;
		}

		HttpURLConnection urlConnection = null; //specific kind of urlconnection that supports http
		BufferedReader reader = null; //reads text from an inputStream

		String[] movies = null;

		try {

			URL url = new URL(urlString); //make a url object to the ombapi api

			urlConnection = (HttpURLConnection) url.openConnection(); //gets a URL Connection to what the url specifies
			urlConnection.setRequestMethod("GET"); 
			urlConnection.connect(); //establishes a communication to the url

			InputStream inputStream = urlConnection.getInputStream(); //gets an inputStream object from the url
			StringBuffer buffer = new StringBuffer(); //object like a string but useful when you have multiple threads
			if (inputStream == null) { //error handling
				return null;
			}
			reader = new BufferedReader(new InputStreamReader(inputStream)); //helps make reading from things like inputStreamReaders more efficient

			String line = reader.readLine();
			while (line != null) { //while there is a next line
				buffer.append(line + "\n"); //adding data from the reader to the buffer
				line = reader.readLine();
			}

			if (buffer.length() == 0) { //no results
				return null;
			}
			String results = buffer.toString();
			results = results.replace("{\"Search\":[","");
			results = results.replace("]}","");
			results = results.replace("},", "},\n"); //formating results

			movies = results.split("\n"); //split results into array of movies
		} 
		catch (IOException e) { //input/output exception
			return null;
		} 
		finally { //always executes
			if (urlConnection != null) {
				urlConnection.disconnect(); //disconnect at the end of session
			}
			if (reader != null) {
				try {
					reader.close(); //close at end of execution
				} 
				catch (IOException e) {
				}
			}
		}

		return movies; //return formatted results as array
	}


	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in); //helps get input from user

		boolean searching = true; //variable that allows user to terminate

		while(searching) {
			System.out.print("Enter a movie name to search for or type 'q' to quit: ");
			String searchTerm = sc.nextLine().trim(); //gets line of imput from user
			if(searchTerm.toLowerCase().equals("q")){
				searching = false;
			}
			else {
				String[] movies = downloadMovieData(searchTerm);
				for(String movie : movies) {
					System.out.println(movie);
				}
			}
		}
		sc.close();
	}
}
