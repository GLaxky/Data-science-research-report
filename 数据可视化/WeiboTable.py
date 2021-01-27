import datetime
import matplotlib.pyplot as plt
import matplotlib.dates as mdates

filePathPart1="D:/HanLP1"

def getData(filePathPart2):
    filePath=filePathPart1+filePathPart2
    data=[]
    with open(filePath) as f:
        for line in f.readlines():
            data.append(int(line.split(" ")[1]))
    return data

# 显示中文必须的步骤
plt.rcParams['font.sans-serif']=['SimHei']
plt.rcParams['axes.unicode_minus'] = False

months = mdates.MonthLocator()  # every month
years_fmt = mdates.DateFormatter('%Y')

fig, ax = plt.subplots()
start = '2019-12-08'
end = '2020-07-01'
startDate = datetime.datetime(int(start.split("-")[0]), int(start.split("-")[1]), int(start.split("-")[2]))
endData = datetime.datetime(int(end.split('-')[0]), int(end.split('-')[1]), int(end.split('-')[2]))
targetDate = startDate
y1=getData("/微博——信任")
y2=getData("/微博——冷静")
y3=getData("/微博——恐慌")
y4=getData("/微博——愤怒")
y5=getData("/微博——担忧")
y6=getData("/微博——鼓舞")
x=[]
i=0
while (targetDate <= endData):
    x.append(targetDate.strftime('%Y-%m-%d'))
    # ax.plot(targetDate.strftime('%Y-%m-%d'),y1[i],'bo', linewidth=1, markersize=3)
    offset = datetime.timedelta(days=1)
    targetDate = targetDate + offset
    i=i+1
ax.plot(x,y1,'-',color="r",label='微博"信任"',alpha=0.6)
ax.plot(x,y2,'-',color="y",label='微博"冷静"',alpha=0.6)
ax.plot(x,y3,'-',color="g",label='微博"恐慌"',alpha=0.6)
ax.plot(x,y4,'-',color="brown",label='微博"愤怒"',alpha=0.6)
ax.plot(x,y5,'-',color="pink",label='微博"担忧"')
ax.plot(x,y6,'-',color="purple",label='微博"鼓舞"',alpha=0.6)

ax.xaxis.set_major_locator(mdates.DayLocator(bymonthday=range(1,32), interval=15))
# round to nearest years.
ax.set_xlim(startDate.strftime('%Y-%m-%d'), endData.strftime('%Y-%m-%d'))
ax.grid(True)
plt.xlabel("日期")
plt.ylabel('特定情绪的评论数/天')
fig.autofmt_xdate()
plt.title("对于微博重点评论的情绪分析")
plt.legend()
plt.show()
