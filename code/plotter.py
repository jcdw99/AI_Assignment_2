from pickle import TRUE
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

from os import walk

SHOW = False
KEEP = False

def dev_IGD(filenames, path):
    dfs = []
    for name in filenames:
        if KEEP:
            dfs.append(pd.read_csv(path + "/keep_done/" + name).iloc[:, 0])
        else:
            dfs.append(pd.read_csv(path + "IGD/" + name).iloc[:, 0])
    nps = [i.to_numpy() for i in dfs]
    arr = np.stack((nps), axis=-1)
    var = np.var(arr, axis=1)
    return var

def mean_IGD(filenames, path):
    dfs = []
    for name in filenames:
        if KEEP:
            dfs.append(pd.read_csv(path + "/keep_done/" + name).iloc[:, 0])
        else:
            dfs.append(pd.read_csv(path + "IGD/" + name).iloc[:, 0])
    nps = [i.to_numpy() for i in dfs]
    arr = np.stack((nps), axis=-1)
    mean = np.mean(arr, axis=1)
    return mean




def plotIGD_ZDT(path):
    raw_filenames = []
    if KEEP:
        raw_filenames = next(walk(path + "/keep_done/"), (None, None, []))[2]
    else:
        raw_filenames = next(walk(path + "/IGD/"), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("IGD"))]
    dev = np.sqrt(dev_IGD(filenames, path))

    for name in filenames:
        if KEEP:
            df = pd.read_csv(path + "/keep_done/" + name).iloc[:, 0]
        else :
            df = pd.read_csv(path + "/IGD/" + name).iloc[:, 0]
        plt.plot(df, label=name)
        plt.plot(df.to_numpy() + dev, label="Upper Bound")
        plt.plot(df.to_numpy() - dev, label="Lower Bound")

        ax = plt.gca()
        ax.set_xlabel('Iteration')
        ax.set_ylabel('Inverted Generational Distance')
        plt.legend()   
        plt.savefig(path + "pics/" + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()
        plt.clf()
    means = mean_IGD(filenames, path)
    ax = plt.gca()
    ax.set_xlabel('Iteration')
    ax.set_ylabel('Inverted Generational Distance')
    plt.plot(means, label="Mean IGD")
    plt.plot(means + dev, label="Upper Bound")
    plt.plot(means - dev, label="Lower Bound")
    plt.legend()
    plt.savefig(path + "pics/" + "AVERAGES" + ".png", bbox_inches='tight')
    plt.show()
    plt.clf()

def getZDT(flag):
    if (flag == 1):
        x1 = np.linspace(0,1,100)
        y1 = 1 - np.sqrt(x1)
        return (x1, y1)
    if (flag == 2):
        x1 = np.linspace(0,1,100)
        y1 = 1 - np.power(x1, 2)
        return (x1, y1)
        
    if (flag == 3):
        x1 = np.linspace(0,1,100)
        y1 = 1 - np.sqrt(x1) - x1 * np.sin(10 * np.pi * x1)
        return (x1, y1)
        
    if (flag == 4):
        pass
        
    if (flag == 6):
        pass

def plotZDT(funcID):
    path = 'logs/ZDT' + str(funcID) + '/'
    raw_filenames = next(walk(path), (None, None, []))[2]
    filenames = [i for i in raw_filenames if not(i.find("ZDT" + str(funcID)))]
    for name in filenames:
        df = pd.read_csv(path + name)
        arr = df.to_numpy()
        front = getZDT(funcID)
        axis = plt.axes()
        archives = []
        xs = []
        ys = []
        for row in arr:
            if ("F1" not in row[0]):
                xs.append(float(row[0]))
                ys.append(float(row[1]))
            else:
                archives.append((xs, ys))
                xs = []
                ys = []
        time = 1
        ax = plt.gca()
        if funcID == 3:
            ax.set_ylim((-.8,1.5))
            ax.set_xlim((-.05,1.1))
        else:
            if funcID == 4:
                ax.set_ylim((0,6))
                ax.set_xlim((0,1)) 
        cols = [('skyblue', .2), ('deepskyblue', .3), ('royalblue', .4),
        ('khaki', .5), ('orange', .6), ('darkorange', .7), ('orangered', .8), ( 'red', .9), ('maroon', 1)]
        coldex = 0
        for a in archives:
            if time == 1:
                namey = "Iteration 0-1000"
            else:
                namey = "Iteration " + str(500*time) + "-" + str(500*(time + 1))
            
            plt.scatter(a[0], a[1], label=namey, color=cols[coldex][0], alpha=cols[coldex][1])
            coldex = coldex + 1
            if coldex > len(archives) - 1:
                coldex = len(archives) - 1
            time = time + 1

        if (front != None):
            axis.plot(front[0], front[1], color='red', label='True Pareto Front')

        ax.set_xlabel('$f_{1}(x)$')
        ax.set_ylabel('$f_{2}(x)$')
        plt.legend()   
        plt.savefig(path + "/pics/" + name[:-4] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()
        plt.clf()

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


# plotZDT(6)
# plotZDT(2)
# plotZDT(3)
# plotZDT2('logs/ZDT2/')
# plotZDT3('logs/ZDT3/')
# plotZDT4('logs/ZDT4/')
# plotZDT6('logs/ZDT6/')
# plotIGD_ZDT('logs/ZDT4/')




# IGD_ZDT1()

