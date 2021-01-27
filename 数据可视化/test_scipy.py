import datetime
import matplotlib.dates as mdates
import matplotlib.pyplot as plt
import analyse_topSearch
import numpy as np
from scipy.stats import norm

# years = mdates.YearLocator()   # every year
months = mdates.MonthLocator()  # every month
years_fmt = mdates.DateFormatter('%Y')

data = analyse_topSearch.get_data()

# 显示中文必须的步骤
plt.rcParams['font.sans-serif']=['SimHei']
plt.rcParams['axes.unicode_minus'] = False

fig, ax = plt.subplots()
start = '2019-12-08'
end = '2020-07-01'
startDate = datetime.datetime(int(start.split("-")[0]), int(start.split("-")[1]), int(start.split("-")[2]))
endData = datetime.datetime(int(end.split('-')[0]), int(end.split('-')[1]), int(end.split('-')[2]))
targetDate = startDate
list1=[]
list2=[]
while (targetDate <= endData):
    list1.append(targetDate.strftime('%Y-%m-%d'))
    list2.append(data[targetDate.strftime('%Y-%m-%d')])
    ax.plot(targetDate.strftime('%Y-%m-%d'),data[targetDate.strftime('%Y-%m-%d')],'bo', linewidth=1, markersize=3)
    offset = datetime.timedelta(days=1)
    targetDate = targetDate + offset
ax.plot(list1,list2,'-',label="折线图")
# format the ticks
# ax.xaxis.set_major_locator(months)
ax.xaxis.set_major_locator(mdates.DayLocator(bymonthday=range(1,32), interval=15))
# round to nearest years.
ax.set_xlim(startDate.strftime('%Y-%m-%d'), endData.strftime('%Y-%m-%d'))
ax.grid(True)
# rotates and right aligns the x labels, and moves the bottom of the
# axes up to make room for them
fig.autofmt_xdate()
plt.annotate("("+'2020-03-16'+","+str(data['2020-03-16'])+")",('2020-03-16',data['2020-03-16']),xytext=(110, 90),arrowprops=dict(arrowstyle='->'))
plt.annotate("("+'2020-01-29'+","+str(data['2020-01-29'])+")",('2020-01-29',data['2020-01-29']),xytext=(0, 90),arrowprops=dict(arrowstyle='->'))
plt.annotate("("+'2020-02-11'+","+str(data['2020-02-11'])+")",('2020-02-11',data['2020-02-11']),xytext=(50, 30),arrowprops=dict(arrowstyle='->'))
plt.annotate("("+'2020-06-11'+","+str(data['2020-06-11'])+")",('2020-06-11',data['2020-06-11']),xytext=(150, 40),arrowprops=dict(arrowstyle='->'))
plt.annotate("("+'2020-06-11'+","+str(data['2020-06-11'])+")",('2020-06-11',data['2020-06-11']),xytext=(150, 40),arrowprops=dict(arrowstyle='->'))
plt.annotate("("+'2020-01-17'+","+str(data['2020-01-17'])+")",('2020-01-17',data['2020-01-17']),xytext=(0, 40),arrowprops=dict(arrowstyle='->'))
plt.annotate("("+'2020-06-22'+","+str(data['2020-06-22'])+")",('2020-06-22',data['2020-06-22']),xytext=(160, 50),arrowprops=dict(arrowstyle='->'))
plt.annotate("("+'2020-06-16'+","+str(data['2020-06-16'])+")",('2020-06-16',data['2020-06-16']),xytext=(190, 5),arrowprops=dict(arrowstyle='->'))
plt.annotate("("+'2020-04-04'+","+str(data['2020-04-04'])+")",('2020-04-04',data['2020-04-04']),xytext=(100, 20),arrowprops=dict(arrowstyle='->'))
plt.axvline(x='2020-01-13',linestyle="--",linewidth=1,color='brown',label='2020-01-13')
plt.axvline(x='2020-02-08',linestyle="--",linewidth=1,color='g',label='2020-02-08')
plt.axvline(x='2020-03-10',linestyle="--",linewidth=1,color='y',label='2020-03-10')

# n,bins,patches=ax.hist(list1,207,normed=1)
# mu =np.mean(list2)
# sigma =np.std(list2)
# y=norm.pdf(ax, mu, sigma)
# plt.plot(list1, y, 'r--')

plt.legend()

plt.xlabel("日期")
plt.ylabel('有关新冠疫情的热搜数/天')
plt.title('从微博热搜角度分析民众对于疫情的关注程度走势图')
plt.show()
