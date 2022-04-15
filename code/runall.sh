./compile.sh
./removeCSV.sh
./removePics.sh


echo "RUNNING ZDT1 TEN DIMENSIONAL FOR 5000 ITERATIONS - ARCHIVE:100"
echo "running ZDT1_D10_a100_5k 1"
java Driver 100 1 25 10 5000 > logs/ZDT1/ZDT1_D10_a100_5k_1.csv
echo "running ZDT1_D10_a100_5k 2"
java Driver 100 1 25 10 5000 > logs/ZDT1/ZDT1_D10_a100_5k_2.csv
echo "running ZDT1_D10_a100_5k 3"
java Driver 100 1 25 10 5000 > logs/ZDT1/ZDT1_D10_a100_5k_3.csv


echo "RUNNING ZDT2 TEN DIMENSIONAL FOR 5000 ITERATIONS - ARCHIVE:100"
echo "running ZDT2_D10_a100_5k 1"
java Driver 100 2 25 10 5000 > logs/ZDT2/ZDT2_D10_a100_5k_1.csv
echo "running ZDT2_D10_a100_5k 2"
java Driver 100 2 25 10 5000 > logs/ZDT2/ZDT2_D10_a100_5k_2.csv
echo "running ZDT2_D10_a100_5k 3"
java Driver 100 2 25 10 5000 > logs/ZDT2/ZDT2_D10_a100_5k_3.csv


echo "RUNNING ZDT3 TEN DIMENSIONAL FOR 5000 ITERATIONS - ARCHIVE:100"
echo "running ZDT3_D10_a100_5k 1"
java Driver 100 3 25 10 5000 > logs/ZDT3/ZDT3_D10_a100_5k_1.csv
echo "running ZDT3_D10_a100_5k 2"
java Driver 100 3 25 10 5000 > logs/ZDT3/ZDT3_D10_a100_5k_2.csv
echo "running ZDT3_D10_a100_5k 3"
java Driver 100 3 25 10 5000 > logs/ZDT3/ZDT3_D10_a100_5k_3.csv

echo "RUNNING ZDT4 TEN DIMENSIONAL FOR 5000 ITERATIONS - ARCHIVE:100"
echo "running ZDT4_D10_a100_5k 1"
java Driver 100 4 25 2 5000 > logs/ZDT4/ZDT4_D10_a100_5k_1.csv
echo "running ZDT4_D10_a100_5k 2"
java Driver 100 4 25 2 5000 > logs/ZDT4/ZDT4_D10_a100_5k_2.csv
echo "running ZDT4_D10_a100_5k 3"
java Driver 100 4 25 2 5000 > logs/ZDT4/ZDT4_D10_a100_5k_3.csv


echo "RUNNING ZDT6 TEN DIMENSIONAL FOR 5000 ITERATIONS - ARCHIVE:100"
echo "running ZDT6_D10_a100_5k 1"
java Driver 100 6 25 10 5000 > logs/ZDT6/ZDT6_D10_a100_5k_1.csv
echo "running ZDT6_D10_a100_5k 2"
java Driver 100 6 25 10 5000 > logs/ZDT6/ZDT6_D10_a100_5k_2.csv
echo "running ZDT6_D10_a100_5k 3"
java Driver 100 6 25 10 5000 > logs/ZDT6/ZDT6_D10_a100_5k_3.csv

echo "=============BATCH DONE================"

python3 plotter.py

