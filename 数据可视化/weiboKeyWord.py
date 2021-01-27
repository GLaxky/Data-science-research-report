import matplotlib.pyplot as plt
import matplotlib.dates as mdates

filePathPart1 = "D:/Documents/Tencent Files/1351039686/FileRecv/微博"


def getData(filePathPart2):
    filePath = filePathPart1 + filePathPart2
    data1 = []
    data2 = []
    with open(filePath) as f:
        for line in f.readlines():
            data1.append((line.split("]")[0])+"]")
            data2.append(int(line.split("]")[1]))
    return [data1, data2]


# 显示中文必须的步骤
plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False

months = mdates.MonthLocator()  # every month
years_fmt = mdates.DateFormatter('%Y')

fig, ax = plt.subplots()
# start = '2019-12-08'
# end = '2020-07-01'
# startDate = datetime.datetime(int(start.split("-")[0]), int(start.split("-")[1]), int(start.split("-")[2]))
# endData = datetime.datetime(int(end.split('-')[0]), int(end.split('-')[1]), int(end.split('-')[2]))
# targetDate = startDate
y1 = getData("1.txt")[1]
y2 = getData("2.txt")[1]
y3 = getData("3.txt")[1]
y4 = getData("4.txt")[1]
x = []
i = 0
# while (targetDate <= endData):
#     x.append(targetDate.strftime('%Y-%m-%d'))
#     # ax.plot(targetDate.strftime('%Y-%m-%d'),y1[i],'bo', linewidth=1, markersize=3)
#     offset = datetime.timedelta(days=10)
#     targetDate = targetDate + offset
#     i=i+1
x = getData("1.txt")[0]

ax.plot(x, y1, '-', color="r", label='微博——"忧虑类"心态词')
ax.plot(x, y2, '-', color="y", label='微博——"希望类"心态词')
ax.plot(x, y3, '-', color="g", label='微博——"害怕类"心态词')
ax.plot(x, y4, '-', color="brown", label='微博——"理智类"心态词')

# ax.xaxis.set_major_locator(mdates.DayLocator(bymonthday=range(1, 32), interval=15))
# round to nearest years.
# ax.set_xlim(startDate.strftime('%Y-%m-%d'), endData.strftime('%Y-%m-%d'))
ax.grid(True)
plt.xlabel("日期区间（10天一个单位）")
plt.ylabel('心态词的频数/天')
fig.autofmt_xdate()
plt.title("对于微博重点评论的心态词分析")
plt.legend()
plt.show()
