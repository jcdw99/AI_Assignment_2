./compile.sh
./removeCSV.sh
./removePics.sh


DIM=10
ARCSIZE=100
TRIALS=2
ITR=5000
itr=5
echo
echo "\033[0;46mStarting Simulation: \033[0m"
for MODE in 1 2
do
   heading=""
   b=true
   if (($MODE == 1))
   then
        heading="SP/"
        echo "\033[0;36mRunning SPECIATION PSO \033[0m"
   else
        heading="IW/"
        b=false
        echo "\033[0;36mRunning INERTIA-WEIGHT PSO \033[0m"
   fi

    for ZDT in 1 2 3 4 6
    do
        echo "\033[0;33mRunning ZDT$ZDT: \033[0m"
        for i in {1..20}
        do
            if (($ZDT == 4)) || (($ZDT == 6))
            then
                echo "\t \033[0;31mRunning DIM:10 Trial: \033[0;33m$i\033[0m"
                java Driver $ARCSIZE $ZDT 25 10 ${ITR} ${b}> logs/${heading}ZDT${ZDT}/ZDT${ZDT}_D10_a${ARCSIZE}_${itr}k_$i.csv
            else 
                echo "\t \033[0;31mRunning DIM:$DIM Trial: \033[0;33m$i\033[0m"
                java Driver $ARCSIZE $ZDT 25 ${DIM} ${ITR} ${b}> logs/${heading}ZDT${ZDT}/ZDT${ZDT}_D${DIM}_a${ARCSIZE}_${itr}k_$i.csv
            fi
        done
    done
done

