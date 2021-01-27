import numpy as np
import matplotlib.pyplot as plt

plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False
n_groups = 4
IDF1 = [2, 0.8293, 0.58627, 0.95424]
IDF2 = [2, 0.58627, 0.47712, 0.58627]

IDF3 = [2, 0.65321, 0.65321, 0.95424]
IDF4 = [2,1.43136,0.73239,1.13033]
fig, ax = plt.subplots()
index = np.arange(n_groups)
bar_width = 0.20
opacity = 0.4
rects2 = plt.bar(index + bar_width, IDF2, bar_width,alpha=opacity, color='g',label='“希望类”心态词')
rects1 = plt.bar(index, IDF1, bar_width, alpha=opacity, color='y', label='“忧虑类”心态词')

rects3 = plt.bar(index + bar_width*2, IDF3, bar_width,alpha=opacity,color='r',label='“恐慌类”心态词')
rects4 = plt.bar(index + bar_width*3, IDF4, bar_width,alpha=opacity,color='b',label='“理智类”心态词')

i=-1
for a, b in zip(index, IDF1):
    i=i+1
    if(i==0):
        ax.text(a, b-0.1, '∞', ha='center', va='bottom')
        continue
    ax.text(a, b+0.03, b, ha='center', va='bottom')

i=-1
for a, b in zip(index, IDF2):
    i=i+1
    if(i==0):
        ax.text(a+ bar_width, b-0.1, '∞', ha='center', va='bottom')
        continue
    ax.text(a+ bar_width, b+0.03, b, ha='center', va='bottom')

i=-1
for a, b in zip(index, IDF3):
    i=i+1
    if(i==0):
        ax.text(a+ bar_width*2, b-0.1, '∞', ha='center', va='bottom')
        continue
    ax.text(a+ bar_width*2, b+0.03, b, ha='center', va='bottom')

i=-1
for a, b in zip(index, IDF4):
    i=i+1
    if(i==0):
        ax.text(a+ bar_width*3, b-0.1, '∞', ha='center', va='bottom')
        continue
    ax.text(a+ bar_width*3, b+0.03, b, ha='center', va='bottom')
plt.xlabel('阶段')
plt.ylabel('IDF')
plt.title('天涯重点帖子——阶段性心态分析')
plt.xticks(index + bar_width, ('阶段一', '阶段二', '阶段三', '阶段四'))
plt.ylim(0, 2)
plt.grid(True,linestyle='--')
plt.legend()
plt.tight_layout()
plt.show()
