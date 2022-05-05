
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import scipy as sp
from scipy.stats import mannwhitneyu


from os import walk

SHOW = False
KEEP = False

def dowhit(funcID):
    iw_dat, sp_dat = get_IGD_winners(funcID, "dat")
    results = mannwhitneyu(sp_dat, iw_dat, alternative='greater')
    print(results)

def get_IGD_winners(funcID, flag):
    iw = True
    mode = 'SP/' if (iw is True) else 'IW/'
    path1 = 'logs/' + mode + 'ZDT' + str(funcID) + '/'
    SP_raw_filenames = next(walk(path1 + "/IGD/"), (None, None, []))[2]
    iw = False
    mode = 'SP/' if (iw is True) else 'IW/'
    path2 = 'logs/' + mode + 'ZDT' + str(funcID) + '/'
    IW_raw_filenames = next(walk(path2 + "/IGD/"), (None, None, []))[2]
    SP_dat = []
    IW_dat = []
    for i in range(len(IW_raw_filenames)):
        IW_dat.append(pd.read_csv(path2 + "IGD/" + IW_raw_filenames[i]).iloc[:, 0])
    for i in range(len(SP_raw_filenames)):
        SP_dat.append(pd.read_csv(path1 + "IGD/" + SP_raw_filenames[i]).iloc[:, 0])
    nps_iw = [i.to_numpy() for i in IW_dat]
    nps_SP = [i.to_numpy() for i in SP_dat]
    arr_iw = np.stack((nps_iw), axis=-1)
    arr_SP = np.stack((nps_SP), axis=-1)

    if flag == "count":
        arr_iw = arr_iw[len(nps_iw) - 1]
        arr_SP = arr_SP[len(nps_SP) - 1]
        iw_win = 0
        sp_win = 0
        for i in range(len(arr_iw)):
            if arr_iw[i] > arr_SP[i]:
                iw_win = iw_win + 1
            else:
                sp_win = sp_win + 1

        # print(arr_iw)
        # print(arr_SP)
        print ("var ZDT" + str(funcID) + " : MGPSO(" + str(iw_win) +")    VS    SPSO(" + str(sp_win) + ")")
    if flag == "var":
        var_iw = np.var(arr_iw, axis=1)
        var_sp = np.var(arr_SP, axis=1)
        max_iw = np.mean(var_iw)
        max_sp = np.mean(var_sp)
        print ("var ZDT" + str(funcID) + " : MGPSO(" + str(max_iw) +")    VS    SPSO(" + str(max_sp) + ")")

    if flag == "dat":
        iw_done = arr_iw[len(nps_iw) - 1]
        sp_done = arr_SP[len(nps_SP) - 1]
        return (iw_done, sp_done)

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


def plotIGD_ZDT(iw, funcID): 
    mode = 'SP/' if (iw is True) else 'IW/'
    legTitle = 'Multi-Modal MGPSO' if (iw is True) else 'MGPSO'
    legTitle = legTitle + ' ZDT' + str(funcID)
    path = 'logs/' + mode + 'ZDT' + str(funcID) + '/'
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
        plt.plot(df, label='IGD')
        plt.plot(df.to_numpy() + dev, label="IGD + StandadDeviation")
        plt.plot(df.to_numpy() - dev, label="IGD - StandadDeviation")

        ax = plt.gca()
        ax.set_xlabel('Iteration')
        ax.set_ylabel('Inverted Generational Distance')
        plt.legend(title=legTitle)   
        plt.savefig(path + "pics/" + name[:-3] + ".png", bbox_inches='tight')
        if SHOW:
            plt.show()
        plt.clf()
    means = mean_IGD(filenames, path)
    ax = plt.gca()
    ax.set_xlabel('Iteration')
    ax.set_ylabel('Inverted Generational Distance')
    plt.plot(means, label="Mean IGD")
    plt.plot(means + dev, label="Mean IGD + Standard Deviation")
    plt.plot(means - dev, label="Mean IGD - Standard Deviation")
    plt.legend(title=legTitle)
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

def plotZDT(funcID, spec):
    mode = 'SP/' if (spec is True) else 'IW/'
    path = 'logs/' + mode + 'ZDT' + str(funcID) + '/'
    legTitle = 'Multi-Modal MGPSO' if (spec is True) else 'MGPSO' 
    legTitle = legTitle + ' ZDT' + str(funcID)
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
                pass
                ax.set_ylim((0,6))
                ax.set_xlim((0,1)) 

            elif funcID == 6:
                ax.set_xlim((0.4,1.1))
            else:
                # for zdt 1, and 2
                ax.set_ylim((0, 1.1))
                ax.set_xlim((0,1.1))

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
        plt.legend(title=legTitle)   
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


for i in [1]: 
    plotZDT(i, True)
    plotZDT(i, False)
    plotIGD_ZDT(True, i)
    plotIGD_ZDT(False, i)

for i in [1,2,3,4,6]: 
    get_IGD_winners(i, "var")

for i in [1,2,3,4,6]:
    print("test result for ZDT" + str(i) + "   ")
    dowhit(i)