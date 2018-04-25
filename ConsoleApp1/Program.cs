using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MPI;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            int finalsum = 0;
            using (new MPI.Environment(ref args))
            {
                Intracommunicator com = MPI.Communicator.world;


                int[] array = new int[100];
                int[] localArray = new int[10];
                int localsum = 0;
                int localsum1 = 0;
                

                for (int i =1;i<=100;i++)
                {
                    array[i - 1] = i;
                }

                if (com.Rank == 0)
                {


                    Random rnd = new Random();
                    int rand = rnd.Next(1, 100);
                    int lider = 0;
                    for (int i=1; i<10;i++)
                    {
                        int rand1 = com.Receive<int>(i, 0);
                        if (rand <= rand1)
                        {
                            lider = i;
                            rand = rand1;
                        }
                    }

                    for (int i = 1; i < 10; i++)
                        com.Send<int>(lider, i, 0);

                }
                else
                {


                    Random rnd = new Random();
                    int rand = rnd.Next(1, 100); // trebuia sa fac altfel rand
                    Console.WriteLine("Rand intoarce:" + rand);
                    com.Send<int>(rand, 0, 0);

                    com.Receive(0, 0,out int lider);
                    Console.WriteLine("Liderul este" + lider);
                }

                com.Dispose();
               //     MPI.Communicator.world.Send(3, 0, 0);
            }

            
        }
    }
}
