import java.util.Scanner;
import java.util.Random;

public class GoL 
{

    final private static int GRIDSIZE = 18;

    /********************************************************************************/
    public static void main ( String args[] )
    {
        boolean[][] board = new boolean[GRIDSIZE][GRIDSIZE];
        char choice;
        int x = 1;
        Scanner sc = new Scanner ( System.in );

        do
        {
            System.out.print ( "Start with a (r)andom board, the (q)ueen bee shuttle or the (g)lider pattern? ");
            choice = sc.next().charAt(0);
        } while ( choice != 'r' && choice != 'q' && choice != 'g' );

        clearGrid (board);

        setup(board,choice);

        do
        {
            System.out.printf ("Viewing generation #%d:\n\n", x++);
            displayGrid(board);
            genNextGrid(board);
            System.out.print ("\n(q)uit or any other key + ENTER to continue: ");
            choice = sc.next().charAt(0);
        } while ( choice != 'q' );

    }

    /********************************************************************************/
    public static void setup (boolean[][] board, char which )
    {
        Random randomNumbers = new Random();

        clearGrid(board);

        if ( which == 'q' )
        {
            // Set up the Queen Bee Shuttle pattern
            board[5][1] = true;board[5][2] = true;board[6][3] = true;board[7][4] = true; 
            board[8][4] = true;board[9][4] = true;board[10][3] = true;board[11][2] = true;
            board[11][1] = true;        
        }
        else if ( which == 'g' )
        {
            // Set up a Glider
            board [17][0] = true; board[16][1] = true; board[15][1] = true;
            board[16][2] = true;
            board [17][2] = true;
        }
        else
        {
            // set up random
            for (int row = 0; row < board.length; row++ )
            {
                for (int col = 0; col < board[row].length; col++ )
                {
                    if ( randomNumbers.nextInt() % 2 == 0 )
                        board[row][col] = true;
                }
            }
        }

    }

    /********************************************************************************/
    public static void displayGrid (boolean[][] grid)
    {
        // Start printing the top row of numbers
        System.out.print ("   ");
        for (int x = 1; x <= grid.length; x++)
        {
            if ((x / 10) != 0)
                System.out.printf ( "%d", x / 10 );
            else
                System.out.print ( " " );
        }

        System.out.println();
        System.out.print( "   " );

        for (int x = 1; x <= grid.length; x++)
        {
            System.out.printf ( "%d", x % 10 );
        }
        System.out.println();

        for (int r = 0; r < grid.length; r++)
        {
            System.out.printf ( "%d", r+1 );
            if (r + 1 < 10)
                System.out.print ( "  " );
            else
                System.out.print ( " " );
            for (int c = 0; c < grid.length; c++)
            {
                if (grid[r][c] == true)
                    System.out.print ( "*" );
                else
                    System.out.print ( " " );
            }
            System.out.println();
        }
    }


    /*******************************************************************************/
	// Method Name       : clearGrid
	// Parameters        : boolean grid[][]
	// Return Value(s)   : void
	// Partners          : none
	// Description       : resets the two dimensional array to all false values

	public static void clearGrid ( boolean[][] grid )
	{
		for (int x=0; x < grid.length; x++)
		{
			for (int y=0; y < grid.length; y++)
			{
				grid[x][y]=false;
			}
		}
	}
   /*******************************************************************************/
	// Method Name       : genNextGrid
	// Parameters        : boolean[][] grid
	// Return Value(s)   : void
	// Partners          : none
	// Description       : generates the next simulation

	public static void genNextGrid ( boolean[][] grid )
	{
	
		int var;
		boolean[][] tempgrid = new boolean[GRIDSIZE][GRIDSIZE];
		for (int x=0; x < 18; x++)
		{
			for (int y=0; y < 18; y++)
			{
				tempgrid[x][y] = grid[x][y];
			}
		}

		for (int p =0; p <= 17; p++)
		{
			for ( int t=0; t <= 17; t++)
			{
				var = countNeighbors(tempgrid, p, t);
					
				if (tempgrid[p][t] == true)
				{
					if ((var == 3)|| (var == 2))
						grid[p][t] = true;

					if ((var < 2)||(var > 3))
						grid[p][t] = false;
				}
		
				if (tempgrid[p][t] == false)
				{
					if (var == 3)
						grid[p][t] = true;
					if ((var <= 2)||(var > 3))
						grid[p][t] = false;
				}
			}
		}
	}
   /*******************************************************************************/
	// Method Name       : countNeighbors
	// Parameters        : final boolean[][] gird, final int row, final int col
	// Return Value(s)   : int number
	// Partners          : none
	// Description       : Counts around cell for live neighbors and reutns how many live neighbors there are.    

	public static int countNeighbors ( final boolean[][] grid, final int row, final int col )
	{

		int number = 0;

		if ((row == 0)&&(col ==0))
		{
			if (grid[0][1] == true)
				number++;
	
			if (grid[1][0] == true)
				number++;
			
			if (grid[1][1] == true)
				number++;	
		}
		
		if ((row == 0)&&(col ==17))
		{
			if (grid[0][16] == true)
				number++;
	
			if (grid[1][16] == true)
				number++;
				
			if (grid[1][17] == true)
				number++;	
		}
	
		if ((row == 17)&&(col ==0))
		{
			if (grid[16][0] == true)
				number++;
	
			if (grid[16][1] == true)
				number++;
			
			if (grid[17][1] == true)
				number++;	
		}
	
		if ((row == 17)&&(col ==17))
		{
			if (grid[16][17] == true)
				number++;
	
			if (grid[16][16] == true)
				number++;
			
			if (grid[17][16] == true)
				number++;	
		}

		if ((row == 0)&&(col >= 1 && col <= 16))
		{
			if (grid[row][col-1] == true)
				number++;
	
			if (grid[row][col+1] == true)
				number++;
			
			if (grid[row+1][col] == true)
				number++;
		
			if (grid[row+1][col-1] == true)
				number++;
		
			if (grid[row+1][col+1] == true)
				number++;	
		}

		if ((row == 17)&&(col >= 1 && col <= 16))
		{
			if (grid[row][col-1] == true)
				number++;
	
			if (grid[row][col+1] == true)
				number++;
			
			if (grid[row-1][col] == true)
				number++;

			if (grid[row-1][col-1] == true)
				number++;
		
			if (grid[row-1][col+1] == true)
				number++;
		}

		if ((col == 0) && (row >= 1 && row <= 16))
		{
			if(grid[row-1][col] == true)
				number++;

			if(grid[row][col+1] == true)
				number++;
		
			if(grid[row+1][col] == true)
				number++;

			if(grid[row-1][col+1] == true)
				number++;

			if(grid[row+1][col+1] == true)
				number++;
		}

		if ((col == 17) && (row >= 1 && row <= 16))
		{
			if(grid[row-1][col] == true)
				number++;

			if(grid[row-1][col-1] == true)
				number++;
		
			if(grid[row][col-1] == true)
				number++;
		
			if(grid[row+1][col-1] == true)
				number++;
		
			if(grid[row+1][col] == true)
				number++;
		}
	
		if ((row >= 1 && row <= 16 ) && ( col >= 1 && col <= 16))	
		{
					
			if (grid[row-1][col-1] == true)
				number++;
	
			if (grid[row-1][col] == true)
				number++;
			
			if (grid[row-1][col+1] == true)
				number++;

			if (grid[row][col+1] == true)
				number++;
		
			if (grid[row+1][col+1] == true)
				number++;

			if (grid[row+1][col] == true)
				number++;
	
			if (grid[row+1][col-1] == true)
				number++;
			
			if (grid[row][col-1] == true)
				number++;
		}

		return number;
	}
}
   /*******************************************************************************/

