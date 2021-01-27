import matplotlib.pyplot as plt

plt.rcParams['font.sans-serif']=['SimHei']
plt.rcParams['axes.unicode_minus'] = False
# Pie chart, where the slices will be ordered and plotted counter-clockwise:
labels = '信任', '冷静','鼓舞','恐慌', '愤怒','担忧'

sum1=16+16+1+6+1+16
sizes1 = [16/sum1, 16/sum1, 1/sum1, 6/sum1,1/sum1,16/sum1]

sum2=16+114+48+68+36+388
sizes2 = [16/sum2, 114/sum2, 48/sum2, 68/sum2,36/sum2,388/sum2]

sum3=442+981+979+173+656+3533
sizes3 = [442/sum3, 981/sum3, 979/sum3, 173/sum3,656/sum3,3533/sum3]

sum4=145+396+356+87+209+1268
sizes4 = [145/sum4, 396/sum4, 356/sum4, 87/sum4,209/sum4,1268/sum4]
explode = (0.1, 0.1, 0.1, 0,0,0)  # only "explode" the 2nd slice (i.e. 'Hogs')

fig1, ax1 = plt.subplots()
ax1.pie(sizes3, explode=explode, labels=labels, autopct='%1.1f%%',
        shadow=True, startangle=90)
ax1.axis('equal')  # Equal aspect ratio ensures that pie is drawn as a circle.

title1="知乎——感染新冠肺炎是一种怎样的体验<总回答数:"+str(sum1)+">"
title2="知乎——新冠肺炎，如果不隔离，人类最坏的结果将会怎样<总回答数:"+str(sum2)+">"
title3="知乎——新冠肺炎给中国带来的积极意义是什么<总回答数:"+str(sum3)+">"
title4="知乎——此次新冠肺炎疫情让你明白了什么道理<总回答数:"+str(sum4)+">"
plt.title(title3)
plt.show()
