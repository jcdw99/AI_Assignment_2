import matplotlib.pyplot as plt
import pandas as pd
import numpy as np


df = pd.read_csv('cool.csv')
arr = df.to_numpy()
x, y = arr.T
plt.scatter(x,y)
plt.show()

