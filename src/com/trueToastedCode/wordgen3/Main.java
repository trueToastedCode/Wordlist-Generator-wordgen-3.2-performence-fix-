package com.trueToastedCode.wordgen3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.out.println("****** wordgen3.2 ******");
		System.out.println("by trueToastedCode\n");
		
		//variables ----------------------------------------------------------------------
		
		//password necessary
		String options;
		int min;
		int max = 0;
		String memory_location;
		
		//password information
		BigInteger password_combinations  = BigInteger.ZERO;
		
		//system
		Scanner sc;
		FileWriter writer = null;
		File file;
		
		//processing
		int block[];
		BigInteger current_block_password_combinations;
		BigInteger created_passwords = BigInteger.ZERO;
		int res;
		
		String now;
		//input ----------------------------------------------------------------------

		//enter options
		System.out.print("enter [options]: ");
		sc = new Scanner(System.in);
		options = sc.nextLine();
		
		//enter min
		System.out.print("enter [min]: ");
		sc = new Scanner(System.in);
		min = Integer.valueOf(sc.nextLine());
		
		//enter max
		boolean maxkleinermin = true;
		while(maxkleinermin == true) {
			System.out.print("enter [max]: ");
			sc = new Scanner(System.in);
			max = Integer.valueOf(sc.nextLine());
			if(max >= min) {
				maxkleinermin = false;
			}else {
				System.out.println("max can not be lesser min ");
			}
		}
		
		//enter memory_location
		System.out.print("enter [memory location]: ");
		sc = new Scanner(System.in);
		memory_location = sc.nextLine();
		
		//setup FileWriter
		file = new File(memory_location);
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//password combinations
		System.out.println("[options] = " + options.length() + " characters");
		for(int blocknumber = min; blocknumber <= max; blocknumber++) {
			password_combinations = password_combinations.add(BigInteger.valueOf(options.length()).pow(blocknumber));
		}
		System.out.println("\nThe following number of password combinations will be generated: " + password_combinations + "\n");
		
		//processing ----------------------------------------------------------------------
		
		//options list
		int options_list[] = new int [options.length()];
		for(int numberforletter = 0; numberforletter < options.length(); numberforletter++) {
			options_list[numberforletter] = numberforletter;
		}
		
		//wait 1s before start
		Thread.sleep(1000);
		now = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
		System.out.println("start at " + now);
		
		//for loop (for every block)
		for(int blocknumber = min; blocknumber <= max; blocknumber++) {
			
			//set current block length an set it to 0
			block = new int[blocknumber];
			for(int blockrow = 0; blockrow < blocknumber; blockrow++) {
				block[blockrow] = 0;
			}
			
			//set current block_password_combinations;
			current_block_password_combinations = BigInteger.valueOf(options.length()).pow(blocknumber);
			
			//reset created passwords
			created_passwords = BigInteger.ZERO;
			
			res = -1;
			while(res == -1) {
				
				//write currentPassword
				for(int blockrow = 0; blockrow < blocknumber; blockrow++) {
					
					if(blockrow == blocknumber-1) {
						writer.write(options.charAt( options_list[ block[ blockrow ] ] ) + "\n");
					}else {
						writer.write(options.charAt( options_list[ block[ blockrow ] ] ));
					}
					
				}
				
				//update block
				block[blocknumber-1]++;
				for(int blockrow = blocknumber-1; blockrow >= 0; blockrow--) {
					
					if(block[blockrow] > options.length()-1 && blockrow > 0) {
						block[blockrow] = 0;
						block[blockrow-1]++;
					}
					
				}
				
				//created_passwords++
				created_passwords = created_passwords.add(BigInteger.ONE);
				
				//compare created_passwords to current_block_password_combinations
				res = created_passwords.compareTo(current_block_password_combinations);
			}
			
		}
		
		writer.flush();
		
		now = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
		System.out.println("\nend at " + now);
		
	}

}
