# -*- coding: utf-8 -*-
"""
Created on Mon Feb 25 14:18:30 2019

@author: Cc
"""


import urllib.parse as up
import lxml.etree as df
import urllib3
import re
import pymysql
import pymysql.cursors
import json

class Book:
    def __init__(self,title,author,image,href,content):
        self.title = title
        self.author = author
        self.image = image
        self.href = href
        self.content = content
    
    def print(self):
        print(self.title,self.author,self.image,self.href,self.content)
class BookInfo:
    def __init__(self, title, author, intro, chapterNames, chapterUrls):
        self.title = title
        self.author = author
        self.intro = intro
        self.chapterNames = chapterNames
        self.chapterUrls = chapterUrls


class Category:
    def __init__(self,name,url):
        self.name = name
        self.url = url
    
    def print(self):
        print(self.name)

#通过url获取room所要的信息
def getHtml(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    bookUrls = getInfo(data,'//div[@class="nav"]/div[@class="item"]/div[@class="image"]/a/@href')
    imageUrls = getInfo(data,'//div[@class="l"]/div[@class="item"]/div[@class="image"]/a/img/@src')
    authors = getInfo(data,'//div[@class="l"]/div[@class="item"]/dl/dt/span/text()')
    titles = getInfo(data,'//div[@class="l"]/div[@class="item"]/dl/dt/a/text()')
    contents = getInfo(data,'//div[@class="l"]/div[@class="item"]/dl/dd/text()')
    
    books = list()
    for i in range(len(bookUrls)):
        book = Book(titles[i], authors[i], imageUrls[i], bookUrls[i], contents[i])
        books.append(book)
        book.print()
    return books

#通过url获取这一类小说
def getBooks(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    names = getInfo(data,'//div[@id="newscontent"]/div[@class="r"]/ul/li/span[@class="s2"]/a/text()')
    urls = getInfo(data,'//div[@id="newscontent"]/div[@class="r"]/ul/li/span[@class="s2"]/a/@href')
    print(names[0])
    print(urls[0])
    categorys = list()
    for i in range(len(names)):
        category = Category(names[i], urls[i])
        categorys.append(category)
        category.print()
    return categorys


#通过url获取这一类小说
def getBooksInfo(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    title = getInfo(data,'//div[@id="info"]/h1/text()')
    author = getInfo(data,'//div[@id="info"]/p/text()')[0]
    intro = getInfo(data, '//div[@id="intro"]/text()')
    chapterNames = getInfo(data, '//div[@id="list"]/dl/dd/a/text()')
    chapterUrls = getInfo(data, '//div[@id="list"]/dl/dd/a/@href')
    bookInfo = BookInfo(title, author, intro, chapterNames, chapterUrls)
    return bookInfo
    
def obj_2_json(obj):
    return{
            'title': obj.title,
            'author': obj.author,
            'intro': obj.intro,
            'chapterNames': obj.chapterNames,
            'chapterUrls': obj.chapterUrls
            }
        


#通过xpath获取数据
def getInfo(data,xpath):
   content = df.HTML(data)
   info = content.xpath(xpath)
   return info


#通过url获取room所要的信息
def getCategory(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    names = getInfo(data,'//div[@class="nav"]/ul/li/a/text()')

    
    categorys = list()
    for i in range(len(names)):
        category = Category(names[i])
        categorys.append(category)
        category.print()
    return categorys

def getSearchResults(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    names = getInfo(data,'//div[@id="search-main"]/div[@class="search-list"]/ul/li/span[@class="s2"]/a/text()')
    urls = getInfo(data,'//div[@id="search-main"]/div[@class="search-list"]/ul/li/span[@class="s2"]/a/@href')
    books = list()
    for i in range(len(names)):
        book = Category(names[i], urls[i])
        books.append(book)
        book.print()
    return books

def getBookContent(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    content = getInfo(data,'//div[@id="content"]/text()')
    return content

    

url_shouye = 'https://www.qu.la/'
def obj_2_json_category(obj):
        return {
                'title': obj.name,
                'url': obj.url
                }
'''books = getHtml(url_shouye)
categorys = getBooks('https://www.qu.la/xuanhuanxiaoshuo/')
data = json.dumps({'code':200, 'books':categorys}, default=obj_2_json_category)
print(data)
'''
url = r'https://www.qu.la/book/101104/5282089.html';
content = getBookContent(url)
print(content)

