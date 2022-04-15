from pickle import TRUE
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

from os import walk

SHOW = True


def plotIGD_ZDT1(path):
    raw_filenames = next(walk(path + "/IGD/"), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("IGD"))]
    for name in filenames:
        df = pd.read_csv(path + "/IGD/" + name).iloc[:, 0]
        
        plt.plot(df, label="IGD_ZDT1")
        ax = plt.gca()
        ax.set_xlabel('Iteration')
        ax.set_ylabel('Inverted Generational Distance')
        plt.legend()   
        plt.savefig(path + "pics/" + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()

def plotIGD_ZDT2(path):
    raw_filenames = next(walk(path + "/IGD/"), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("IGD"))]
    for name in filenames:
        df = pd.read_csv(path + "/IGD/" + name).iloc[:, 0]
        
        plt.plot(df, label='IGD ZDT2')
        ax = plt.gca()
        ax.set_xlabel('Iteration')
        ax.set_ylabel('Inverted Generational Distance')
        plt.legend()   
        plt.savefig(path + "pics/" + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()

def plotIGD_ZDT3(path):
    raw_filenames = next(walk(path + "/IGD/"), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("IGD"))]
    for name in filenames:
        df = pd.read_csv(path + "/IGD/" + name).iloc[:, 0]
        
        plt.plot(df, label='IGD ZDT3')
        ax = plt.gca()
        ax.set_xlabel('Iteration')
        ax.set_ylabel('Inverted Generational Distance')
        plt.legend()   
        plt.savefig(path + "pics/" + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()

def plotIGD_ZDT4(path):
    raw_filenames = next(walk(path + "/IGD/"), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("IGD"))]
    for name in filenames:
        df = pd.read_csv(path + "/IGD/" + name).iloc[:, 0]
        
        plt.plot(df, label='IGD ZDT4')
        ax = plt.gca()
        ax.set_xlabel('Iteration')
        ax.set_ylabel('Inverted Generational Distance')
        plt.legend()   
        plt.savefig(path + "pics/" + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()


def plotIGD_ZDT6(path):
    raw_filenames = next(walk(path + "/IGD/"), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("IGD"))]
    for name in filenames:
        df = pd.read_csv(path + "/IGD/" + name).iloc[:, 0]
        
        plt.plot(df, label='IGD ZDT6')
        ax = plt.gca()
        ax.set_xlabel('Iteration')
        ax.set_ylabel('Inverted Generational Distance')
        plt.legend()   
        plt.savefig(path + "pics/" + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
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
        axis.scatter(x,y, label=name)
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
        axis.scatter(x,y, label=name)
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
        axis.scatter(x,y, label=name)
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

def plotZDT4(path):
    raw_filenames = next(walk(path), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("ZDT4"))]
    for name in filenames:
        df = pd.read_csv(path + name)
        arr = df.to_numpy()
        x, y = arr.T
        axis = plt.axes()
        axis.scatter(x,y, label=name)
        ax = plt.gca()
        ax.set_xlabel('$f_{1}(x)$')
        ax.set_ylabel('$f_{2}(x)$')
        plt.legend()   
        plt.savefig(path + '/pics/' + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()

def plotZDT6(path):
    raw_filenames = next(walk(path), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("ZDT6"))]
    for name in filenames:
        df = pd.read_csv(path + name)
        arr = df.to_numpy()
        x, y = arr.T
        f1 = np.linspace(0,1,100)
        f2 = 1 - np.power(f1, 2)
        axis = plt.axes()
        axis.scatter(x,y, label=name)
        axis.plot(f1, f2, color='red', label='True Pareto Front')
        ax = plt.gca()
        ax.set_xlabel('$f_{1}(x)$')
        ax.set_ylabel('$f_{2}(x)$')
        plt.legend()   
        plt.savefig(path + '/pics/' + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()



def plotOne(path):
    df = pd.read_csv(path)
    arr = df.to_numpy()
    x, y = arr.T
    axis = plt.axes()
    axis.scatter(x,y)
    ax = plt.gca()
    ax.set_xlabel('$f_{1}(x)$')
    ax.set_ylabel('$f_{2}(x)$')
    plt.legend()   



# plotZDT1('logs/ZDT1/')
# plotZDT2('logs/ZDT2/')
# plotZDT3('logs/ZDT3/')
# plotZDT4('logs/ZDT4/')
# plotZDT6('logs/ZDT6/')

plotIGD_ZDT1('logs/ZDT1/')
plotIGD_ZDT2('logs/ZDT2/')
plotIGD_ZDT3('logs/ZDT3/')
plotIGD_ZDT4('logs/ZDT4/')
plotIGD_ZDT6('logs/ZDT6/')



# IGD_ZDT1()

