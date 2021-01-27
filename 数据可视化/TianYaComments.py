import json
import requests
import xlwt
from bs4 import BeautifulSoup
import re

def getUrlList():
    pass;

def getAndSaveThisPostComment(url,excelSheet,num):
    try:
        partList=url.split(r'1.shtml')
        urlPart1=partList[0]
        urlPart2=".shtml"

        # 爬取第一页
        r = requests.get(url)
        r.raise_for_status()
        r.encoding= r.apparent_encoding
        soup = BeautifulSoup(r.text, 'html.parser')
        tmp=soup.find('script',type="text/javascript")
        pattern = re.compile(r'pageCount : [1-9]\d*')
        page=int(pattern.findall(str(tmp))[0].split(' ')[2])
        postName=soup.title.string
        postTime=''
        listSpan = soup.find('div',class_="atl-info").findAll('span')

        for i in range(0,4):
            if(i==0):
                continue
            if(i==1):
                postTime=listSpan[i].string[3:]
                excelSheet.write(0,0,postName)#发帖时间
            if(i==2):
                excelSheet.write(0,1,listSpan[i].string)#点击量
            if(i==3):
                excelSheet.write(0,2,listSpan[i].get("title"))#回帖与评论
        excelSheet.write(0,3,postName)#帖子名字
        row=1
        for i in soup.findAll('div',class_="atl-item"):
            if(i.get('js_restime')==None):
                bbstime=postTime
                tmp1=i.find('div', class_='bbs-content')
                bbsContent="".join(str(tmp1).replace('<br/>',' ').split('<div class="bbs-content')[1].split()).split(">")[1][0:-5]
                excelSheet.write(row,0,bbstime)
                excelSheet.write(row,1,bbsContent)
                row=row+1
            else:
                bbstime=i.get('js_restime')
                tmp1=i.find('div', class_='bbs-content')
                bbsContent="".join(str(tmp1).replace('<br/>',' ').split('<div class="bbs-content')[1].split()).split(">")[1][0:-5]
                excelSheet.write(row,0,bbstime)
                excelSheet.write(row,1,bbsContent)
                row=row+1
                for j in i.findAll("li"):
                    irtime=j.get('_replytime')
                    excelSheet.write(row,0,irtime)
                    tmp2=j.find('span',class_="ir-content")
                    if(tmp2.a==None):
                        ircontent=tmp2.string
                    else:
                        ircontent=str(tmp2).split('</a>')[1].strip()[1:-7]
                    excelSheet.write(row,1,ircontent)
                    row=row+1
        print("第"+str(num)+"个链接的"+"第1页完成")
        # 接下来爬取剩下的页的信息
        for k in range(2,page+1):
            url2=urlPart1+str(k)+urlPart2
            try:
                r = requests.get(url2)
                r.raise_for_status()
                r.encoding= r.apparent_encoding
            except:
                print("第"+str(num)+"个链接的"+"第"+str(k)+"页"+"爬取失败")
            soup = BeautifulSoup(r.text, 'html.parser')
            for i in soup.findAll('div',class_="atl-item"):
                if(i.get('js_restime')==None):
                    bbstime=postTime
                    tmp1=i.find('div', class_='bbs-content')
                    bbsContent="".join(str(tmp1).replace('<br/>',' ').split('<div class="bbs-content')[1].split()).split(">")[1][0:-5]
                    excelSheet.write(row,0,bbstime)
                    excelSheet.write(row,1,bbsContent)
                    row=row+1
                else:
                    bbstime=i.get('js_restime')
                    tmp1=i.find('div', class_='bbs-content')
                    bbsContent="".join(str(tmp1).replace('<br/>',' ').split('<div class="bbs-content')[1].split()).split(">")[1][0:-5]
                    excelSheet.write(row,0,bbstime)
                    excelSheet.write(row,1,bbsContent)
                    row=row+1
                    for j in i.findAll("li"):
                        irtime=j.get('_replytime')
                        excelSheet.write(row,0,irtime)
                        tmp2=j.find('span',class_="ir-content")
                        if(tmp2.a==None):
                            ircontent=tmp2.string
                        else:
                            ircontent=str(tmp2).split('</a>')[1].strip()[1:-7]
                        excelSheet.write(row,1,ircontent)
                        row=row+1
            print("第"+str(num)+"个链接的"+"第"+str(k)+"页完成")
    except:
        print(url)
    return



def getUrlList(path):
    list=[]
    with open(path,'r', encoding='utf-8') as f:
        list.extend(f.readlines())
    return list



filePaths=["D:/Documents/Tencent Files/1351039686/FileRecv/天涯疫情url.txt","D:/Documents/Tencent Files/1351039686/FileRecv/天涯李文亮url.txt",
           "D:/Documents/Tencent Files/1351039686/FileRecv/天涯新冠肺炎url.txt","D:/Documents/Tencent Files/1351039686/FileRecv/天涯华南海鲜市场url.txt"]
workBook=xlwt.Workbook(encoding ="utf-8",style_compression=0)
urlList = []
for i in filePaths:
    urlList.extend(getUrlList(i))
workBook=xlwt.Workbook(encoding ="utf-8",style_compression=0)
count=0
for url in urlList:
    getAndSaveThisPostComment(url,workBook.add_sheet(str(count)),count)
    count=count+1
    print("第"+str(count-1)+"个链接爬取完成")
workBook.save('TianYaComments.xls')
