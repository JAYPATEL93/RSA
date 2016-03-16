import java.util.Scanner;
import java.util.Random;
import java.util.*;
import java.io.*;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;

public class Test{
  
  static int p, q, p_rand, q_rand;
  
  public static void main(String args[]){
    Scanner in = new Scanner(System.in);
    
    System.out.println("Press 'y' to enter two prime numbers or 'n' to select from file: ");
    char response = in.next().charAt(0);
    
    // asking the user for prime numbers
    if (response == 'y'){ 
      System.out.println("Enter first prime number: ");
      p = in.nextInt();
      boolean check = isPrime(p);
      
      if (check == false){
        while (check != true){
          System.out.println( p + " is not prime. Enter first prime again: ");
          p = in.nextInt();
          check = isPrime(p);
        }
      }
      
      System.out.println("Enter second prime number: ");
      q = in.nextInt();
      check = isPrime(q);
      
      if (check == false){
        while (check != true){
          System.out.println( q + " is not prime. Enter second prime again: ");
          q = in.nextInt();
          check = isPrime(q);
        }
      }
   }
    
    // select prime numbers from the file.
    if (response == 'n'){
      Random rand = new Random();
      //two random numbers for p and q
      p_rand = rand.nextInt(20) + 1;
      q_rand = rand.nextInt(20) + 1;
      
      while (p_rand == q_rand){
        p_rand = rand.nextInt(20) + 1;
        q_rand = rand.nextInt(20) + 1;
      }     
      
      //reading the primes from the file prime.txt
      File file = new File("prime.txt");
      BufferedReader reader = null;
      int counter = 1;
      try {
        reader = new BufferedReader(new FileReader(file));
        String text = null;
        
        while ((text = reader.readLine()) != null) {
          if (counter == p_rand)
            p = Integer.parseInt(text);
          if (counter == q_rand)
            q = Integer.parseInt(text);
          counter++;
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (reader != null) {
            reader.close();
          }
        } catch (IOException e) {
        }
      }   
    }
    
    System.out.println("p: " + p);
    System.out.println("q: " + q);
    int pForArray = p;
    int qForArray = q;
    
    //length of prime p and q and create p and q arrays.
    int p_length = (int)(Math.log10(p)+1);
    int q_length = (int)(Math.log10(q)+1);
    int [] parray = new int [p_length];
    int [] qarray = new int [q_length];
    
    //making two dimentional array for arithmatic operation of two prime arrays 
    int[][] twoDim = new int[p_length * 2][q_length * 2];
    
    //storing the value of p and q in array p_array and q_array
    int tenth_p = 1, tenth_q = 1;
    for (int i = 0; i < p_length-1; i++){ tenth_p = tenth_p * 10;}
    for (int i = 0; i < q_length-1; i++){ tenth_q = tenth_q * 10;}
    
    //p to parray
    for (int i = 0; i < p_length; i++){
      parray[i] = pForArray/tenth_p;
      pForArray = pForArray % tenth_p;
      tenth_p = tenth_p/10;
    }
    
    //q to qarray
    for (int i = 0; i < q_length; i++){
      qarray[i] = qForArray / tenth_q;
      qForArray = qForArray % tenth_q;
      tenth_q = tenth_q/10;
    }
    
    // using two dimentional array to perform arithmetic operation between two arrays of integers 
    int x = 0, y = 0;
    for (int i = 0; i < p_length; i++){
      for (int j = 0; j < q_length; j++){
        int pp = parray[i];
        int qq = qarray[j];
        int nn = pp * qq;
        int upX, upY;
        upX = x;
        upY = y;
        int div = nn / 10;
        twoDim[upX][upY] = div;
        int rem = nn % 10;
        twoDim[upX+1][upY+1] = rem; 
        y=y+2;
        if (y >= q_length * 2)
          y = 0;
      }
      x=x+2;
      if (x >= p_length * 2)
        x = 0;
    }
    
    //finally: arrray n = array p * array q   
    int[] nn = new int[50];
    int carry = 0, sum = 0, counter = 0, remainder = 0, divider = 0;
    int posX = (p_length *2)-1; 
    int posY = (q_length *2)-1;
    int x_fixed = posX;
    int i = (q_length *2) - 1;
    
    // moving in -y direction of 2D array
    while (i >= 0){
      while(posX < p_length *2 && posY < q_length *2 && posX >= 0 && posY >= 0){
        sum = sum + twoDim[posX][posY];
        posX = posX - 1;
        posY = posY + 1;
      }    
      sum = sum + carry;
      divider = sum / 10; 
      carry = divider;
      remainder = sum % 10;
      nn [counter] = remainder; 
      counter++;
      sum = 0;
      i = i - 2;
      posY = i;
      posX = x_fixed;
    }
    posX = (p_length *2)-2; 
    posY = 0;
    int j = (p_length *2) - 2;
    int y_fixed = 0;
    
    //moving in -x direction of 2D array 
    while (j >= 0){
      while(posX < p_length *2 && posY < q_length *2 && posX >= 0 && posY >= 0){
        sum = sum + twoDim[posX][posY];
        posX = posX - 1;
        posY = posY + 1;
      }    
      sum = sum + carry;
      divider = sum / 10; 
      carry = divider;
      remainder = sum % 10;
      nn [counter] = remainder; 
      counter++;
      sum = 0;
      j = j - 2;
      posX = j;
      posY = y_fixed;
    }
    
    if (remainder > 0){
      counter = counter + 1;
      nn[counter] = remainder;
    }
    
    
    int [] n = new int[counter - 1];
    for (int s = 0; s < counter - 1; s++){
      n[s] = nn[s];  
    }
    for(i = 0; i < n.length / 2; i++)
    {
      int temp = n[i];
      n[i] = n[n.length - i - 1];
      n[n.length - i - 1] = temp;
    }
    ///////////////////////////////////////
    for (int s = 0; s < counter - 1; s++){
    }
    int pForTheta = p - 1;
    int qForTheta = q - 1;
    
    int p_len = (int)(Math.log10(pForTheta)+1);
    int q_len = (int)(Math.log10(qForTheta)+1);
    int [] parr = new int [p_len];
    int [] qarr = new int [q_len];
     
    int[][] two = new int[p_len * 2][q_len * 2];
    
    //storing the value of p and q in array p_array and q_array
    int ten_p = 1, ten_q = 1;
    for ( i = 0; i < p_len-1; i++){ ten_p = ten_p * 10;}
    for ( i = 0; i < q_len-1; i++){ ten_q = ten_q * 10;}
    
    //p to parray
    for (i = 0; i < p_len; i++){
      parr[i] = pForTheta/ten_p;
      pForTheta = pForTheta % ten_p;
      ten_p = ten_p/10;
    }
    
    //q to qarray
    for (i = 0; i < q_len; i++){
      qarr[i] = qForTheta / ten_q;
      qForTheta = qForTheta % ten_q;
      ten_q = ten_q/10;
    }
    
    // using two dimentional arrayy to perform arithmetic operation 
    int xx = 0, yy = 0;
    for (i = 0; i < p_len; i++){
      for (j = 0; j < q_len; j++){
        int pp = parr[i];
        int qq = qarr[j];
        int nTheta = pp * qq;
        int upX, upY;
        upX = xx;
        upY = yy;
        int div = nTheta / 10;
        twoDim[upX][upY] = div;
        int rem = nTheta % 10;
        twoDim[upX+1][upY+1] = rem; 
        yy=yy+2;
        if (yy >= q_len * 2)
          yy = 0;
      }
      xx=xx+2;
      if (xx >= p_len * 2)
        xx = 0;
    }
    
    int[] nTheta = new int[50];
    carry = 0;
    sum = 0;
    counter = 0;
    remainder = 0;
    divider = 0;
    posX = (p_len *2)-1; 
    posY = (q_len *2)-1;
    x_fixed = posX;
    i = (q_len *2) - 1;
    
    // moving in -y direction of 2D array
    while (i >= 0){
      while(posX < p_len *2 && posY < q_len *2 && posX >= 0 && posY >= 0){
        sum = sum + twoDim[posX][posY];
        posX = posX - 1;
        posY = posY + 1;
      }    
      sum = sum + carry;
      divider = sum / 10; 
      carry = divider;
      remainder = sum % 10;
      nTheta [counter] = remainder; 
      counter++;
      sum = 0;
      i = i - 2;
      posY = i;
      posX = x_fixed;
    }
    posX = (p_len *2)-2; 
    posY = 0;
    j = (p_len *2) - 2;
    y_fixed = 0;
    
    //moving in -x direction of 2D array 
    while (j >= 0){
      while(posX < p_len *2 && posY < q_len *2 && posX >= 0 && posY >= 0){
        sum = sum + twoDim[posX][posY];
        posX = posX - 1;
        posY = posY + 1;
      }    
      sum = sum + carry;
      divider = sum / 10; 
      carry = divider;
      remainder = sum % 10;
      nTheta [counter] = remainder; 
      counter++;
      sum = 0;
      j = j - 2;
      posX = j;
      posY = y_fixed;
    }
    
    if (remainder > 0){
      counter = counter + 1;
      nTheta[counter] = remainder;
    }
    
    
    int [] theta = new int[counter - 1];
    for (int s = 0; s < counter - 1; s++){
      theta[s] = nTheta[s];  
    }
    for(i = 0; i < theta.length / 2; i++)
    {
      int temp = theta[i];
      theta[i] = theta[theta.length - i - 1];
      theta[theta.length - i - 1] = temp;
    }

    long result_theta = 0;
    for( i = 0; i < theta.length; i++) result_theta += Math.pow(10,i) * theta[theta.length - i - 1];
    
    long result_n = 0;
    for( i = 0; i < n.length; i++) result_n += Math.pow(10,i) * n[n.length - i - 1];
    System.out.println("n: " + result_n);
    
    //choosing the value of e
    int e;
    if ( theta.length >= 9){
      e = 68956417;
    }
    else if ( theta.length > 5){
      e =  65537;
    }
    else 
      e = 5;
    System.out.println("e: " + e);
  
    //creating the value of d
  long k = 1;
  long d = 0;
  long rem = 1;
  while(rem != 0){
    d = (k * result_theta) + 1;
    rem = d % e;
    d = d / e;
    k++;
  }
  System.out.println("d: " + d);
  
  
  //working with the user message
  
  char[] charArray ={ '\0','\0', '0', '0', '0', ' ', '!', '"', '#', '$', +
                      '%', '&', ' ', '(', ')', '*', '+', ',', '-', '.',  +
                      '/', '0', '1', '2', '3', '4', '5', '6', '7', '8',  +
                      '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B',  +
                      'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',  +
                      'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',  +
                      'W', 'X', 'Y', 'Z', '[', '\0', ']', '^', '_', '`', +
                      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',  +
                      'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',  +
                      'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~'  }; 

  //text to numbers from above chart
  String text;
  System.out.println("Enter message:");
  text = in.next();
  int [] charToNum = new int [text.length() * 2];
  counter = 0;
  for (i = 0; i < text.length(); i++){
    char c = text.charAt(i);
    int ascii = 0;
    while (ascii < 100){
      if (c == charArray[ascii]){
        charToNum[counter] = ascii / 10;
        counter++;
        charToNum[counter] = ascii % 10;
      }
      ascii++;
    }
    counter++;
  }
  
  System.out.println("Blocking size of 4:");
  
  long int_msg = 0;
  for( i = 0; i < charToNum.length; i++) int_msg += Math.pow(10,i) * charToNum[charToNum.length - i - 1];
  System.out.println("text to ascii numbers: " + int_msg);
  
  
  //encrypting messge 
  long c = 1;
  for ( i = 0 ; i < e ; i++ ){
    c = c * int_msg ;
    c = c % result_n;
  }
  System.out.println("Encrypted: " + c);
  
  //decrypting message
  for ( i = 0 ; i < d; i++ ){
    c  = ( c * int_msg ) % result_n;
  }
  System.out.println("Decrypted: " + c);
}
  
  public static boolean isPrime(int n) {
    //check if n is a multiple of 2
    if (n%2==0) return false;
    //if not, then just check the odds
    for(int i=3;i*i<=n;i+=2) {
      if(n%i==0)
        return false;
    }
    return true;
  }
  
  public static int gcd(int p, int q) {
        while (q != 0) {
            int temp = q;
            q = p % q;
            p = temp;
        }
        return p;
    }

}