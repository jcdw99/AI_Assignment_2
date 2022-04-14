from pickle import TRUE
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

from os import walk

SHOW = True


def IGD_ZDT1():
    df = pd.read_csv("logs/ZDT1/IGD/IGD_SS25_A50_I100.csv").iloc[:, 0]
    print(df.head())
    plt.plot(df)
    plt.show()


def plotZDT1(path):
    raw_filenames = next(walk(path), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("ZDT1"))]
    for name in filenames:
        df = pd.read_csv(path + name)
        arr = df.to_numpy()
        x, y = arr.T
        x1 = np.linspace(0,1,100)
        y1 = 1 - np.sqrt(x1)
        axis = plt.axes()
        axis.scatter(x,y, label="MGPSO On ZDT1")
        axis.plot(x1, y1, color='red', label='True Pareto Front')
        ax = plt.gca()
        ax.set_ylim((0,1.1))
        ax.set_xlim((0,1.1))
        ax.set_xlabel('$f_{1}(x)$')
        ax.set_ylabel('$f_{2}(x)$')
        plt.legend()   
        plt.savefig(path + "/pics/" + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()

def plotZDT2(path):
    raw_filenames = next(walk(path), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("ZDT2"))]
    for name in filenames:
        df = pd.read_csv(path + name)
        arr = df.to_numpy()
        x, y = arr.T
        x1 = np.linspace(0,1,100)
        y1 = 1 - np.power(x1, 2)
        axis = plt.axes()
        axis.scatter(x,y, label="MGPSO On ZDT2")
        axis.plot(x1, y1, color='red', label='True Pareto Front')
        ax = plt.gca()
        ax.set_ylim((0,1.1))
        ax.set_xlim((0,1.1))
        ax.set_xlabel('$f_{1}(x)$')
        ax.set_ylabel('$f_{2}(x)$')
        plt.legend()   
        plt.savefig(path + "/pics/"  + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()

def plotZDT3(path):
    raw_filenames = next(walk(path), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("ZDT3"))]
    for name in filenames:
        df = pd.read_csv(path + name)
        arr = df.to_numpy()
        x, y = arr.T
        x1 = np.linspace(0,1,100)
        y1 = 1 - np.sqrt(x1) - x1 * np.sin(10 * np.pi * x1)
        axis = plt.axes()
        axis.scatter(x,y, label="MGPSO On ZDT3")
        axis.plot(x1, y1, color='red', label='True Pareto Front')
        ax = plt.gca()
        ax.set_ylim((-0.8,1.2))
        ax.set_xlim((0,1.1))
        ax.set_xlabel('$f_{1}(x)$')
        ax.set_ylabel('$f_{2}(x)$')
        plt.legend()   
        plt.savefig(path + '/pics/' + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()


plotZDT1('logs/ZDT1/')
plotZDT2('logs/ZDT2/')
plotZDT3('logs/ZDT3/')

#IGD_ZDT1()

