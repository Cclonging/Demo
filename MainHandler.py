# -*- coding: utf-8 -*-
"""
Created on Mon Feb 25 14:11:27 2019

@author: Cc
"""


import urllib.parse as up
from concurrent.futures import ThreadPoolExecutor
from tornado.concurrent import run_on_executor
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import tornado.gen
import json
import traceback
import lxml.etree as df
import urllib3
urllib3.disable_warnings()
import re
import pymysql
import pymysql.cursors
 
def add(a,b):
    c = int(a) + int(b)
    return str(c)

class Book:
    def __init__(self,title,author,image,href,content):
        self.title = title
        self.author = author
        self.image = image
        self.href = href
        self.content = content
    
    def print(self):
        print(self.title,self.author,self.image,self.href,self.content)


class Category:
    def __init__(self,name, url):
        self.name = name
        self.url = url
    
    def print(self):
        print(self.name, self.url)    
        
class BookInfo:
    def __init__(self, title, author, intro, chapterNames, chapterUrls):
        self.title = title
        self.author = author
        self.intro = intro
        self.chapterNames = chapterNames
        self.chapterUrls = chapterUrls
        
class JSONObj:
    def __init__(self, url):
        self.__dict__ = url

def obj_2_json(obj):
        return {
                'title': obj.title,
                'author': obj.author,
                'image': obj.image,
                'href': obj.href,
                'content': obj.content
                }
def obj_2_json_bookInfo(obj):
    return{
            'title': obj.title,
            'author': obj.author,
            'intro': obj.intro,
            'chapterNames': obj.chapterNames,
            'chapterUrls': obj.chapterUrls
            }
def handle(d):
    return Book(d['title'],d['author'],d['image'],d['href'],d['content'])
        
def obj_2_json_category(obj):
        return {
                'title': obj.name,
                'url': obj.url
                }
        
#通过url获取room所要的信息
def getHtml(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    bookUrls = getInfo(data,'//div[@id="hotcontent"]/div[@class="l"]/div[@class="item"]/div[@class="image"]/a/@href')
    imageUrls = getInfo(data,'//div[@id="hotcontent"]/div[@class="l"]/div[@class="item"]/div[@class="image"]/a/img/@src')
    authors = getInfo(data,'//div[@id="hotcontent"]/div[@class="l"]/div[@class="item"]/dl/dt/span/text()')
    titles = getInfo(data,'//div[@id="hotcontent"]/div[@class="l"]/div[@class="item"]/dl/dt/a/text()')
    contents = getInfo(data,'//div[@id="hotcontent"]/div[@class="l"]/div[@class="item"]/dl/dd/text()')
    
    books = list()
    for i in range(len(bookUrls)):
        book = Book(titles[i].replace('\n', ''), authors[i].replace('\n', ''), imageUrls[i].replace('\n', ''), bookUrls[i].replace('\n', ''), contents[i].replace('\n', ''))
        books.append(book)
        book.print()
    return books

#通过url获取room所要的信息
def getCategory(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    names = getInfo(data,'//div[@class="nav"]/ul/li/a/text()')
    urls = getInfo(data,'//div[@class="nav"]/ul/li/a/@href')
    
    categorys = list()
    for i in range(len(names)):
        category = Category(names[i], urls[i])
        categorys.append(category)
        category.print()
    return categorys

#通过url获取这一类小说
def getBooks(url):
    print('hhh')
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

#获取查询列表
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

#获取书本内容
def getBookContent(url):
    # print('hhh')
    http = urllib3.PoolManager()
    response = http.request('GET',url)
    data = response.data.decode('UTF-8')
    content = getInfo(data,'//div[@id="content"]/text()')
    return content

#通过xpath获取数据
def getInfo(data,xpath):
   content = df.HTML(data)
   info = content.xpath(xpath)
   return info

'''
处理爬取精品推荐的请求
'''
class MainHandler(tornado.web.RequestHandler):
    executor = ThreadPoolExecutor(32)
    @tornado.gen.coroutine
    def get(self):
        '''get接口'''
        url_shouye = 'https://www.qu.la/'

        books = getHtml(url_shouye)
        categorys = getCategory(url_shouye)
            
        result1 = json.dumps({'books': books}, default=obj_2_json)
        print(result1)
        result = json.dumps({'code': 200,'intro': result1, 'category': categorys}, default=obj_2_json_category)
        self.write(result)

            
    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def post(self):
        yield self.coreOperation()
 
    @run_on_executor
    def coreOperation(self):
        '''主函数'''
        try:
            print('doPost')
        except Exception:
            print('doPost, Exception')
'''
处理爬取小说种类的请求
'''
class MainHandler2(tornado.web.RequestHandler):
    executor = ThreadPoolExecutor(32)
    @tornado.gen.coroutine
    def get(self):
        print("doGet")
        
            
    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def post(self):
        a = self.get_argument("lin", None)
        print('a'+a)
        yield self.coreOperation(a)
 
    @run_on_executor
    def coreOperation(self, lin):
        '''主函数'''
        try:
            print('lin' + lin);
            booksurl = getBooks(lin)
            result = json.dumps({'code':200,'books':booksurl}, default=obj_2_json_category)
            self.write(result)
            print('doPost')
        except Exception:
            result = json.dumps({'code':500, 'books':'none'})
            self.write(result)
            print('doPost, Exception')
            
'''
处理爬取小说信息的类
'''
class MainHandler3(tornado.web.RequestHandler):
    executor = ThreadPoolExecutor(32)
    @tornado.gen.coroutine
    def get(self):
        print("doGet")
        
            
    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def post(self):
        a = self.get_argument("lin", None)
        yield self.coreOperation(a)
 
    @run_on_executor
    def coreOperation(self, lin):
        '''主函数'''
        try:
            print('lin' + lin);
            bookInfo = getBooksInfo(lin)
            result = json.dumps({'code':200,'books':bookInfo}, default=obj_2_json_bookInfo)
            self.write(result)
            print('doPost')
        except Exception:
            result = json.dumps({'code':500, 'books':'none'})
            self.write(result)
            print('doPost, Exception')
            
'''
处理爬取小说种类的请求
'''
class MainHandler4(tornado.web.RequestHandler):
    executor = ThreadPoolExecutor(32)
    @tornado.gen.coroutine
    def get(self):
        print("doGet")
        
            
    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def post(self):
        a = self.get_argument("lin", None)
        print('a'+a)
        yield self.coreOperation(a)
 
    @run_on_executor
    def coreOperation(self, lin):
        '''主函数'''
        try:
            print('doPost')
            url = r'https://sou.xanbhx.com/search?siteid=qula&q=' + up.quote(lin);
            print(url)
            booksurl = getSearchResults(url)
            result = json.dumps({'code':200,'books':booksurl}, default=obj_2_json_category)
            self.write(result)
            
        except Exception:
            result = json.dumps({'code':500, 'books':'爬虫错误'})
            self.write(result)
            print('doPost, Exception')
            
'''
处理爬取小说内容
'''
class MainHandler5(tornado.web.RequestHandler):
    executor = ThreadPoolExecutor(32)
    @tornado.gen.coroutine
    def get(self):
        print("doGet")
        
            
    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def post(self):
        a = self.get_argument("lin", None)
        print('a'+a)
        yield self.coreOperation(a)
 
    @run_on_executor
    def coreOperation(self, lin):
        '''主函数'''
        try:
            print('doPost')
            content = getBookContent(lin)
            result = json.dumps({'code':200,'content':content}, default=obj_2_json_category)
            self.write(result)
            
        except Exception:
            result = json.dumps({'code':500, 'books':'爬虫错误'})
            self.write(result)
            print('doPost, Exception')
           
if __name__ == "__main__":
    tornado.options.parse_command_line()
    app = tornado.web.Application(handlers=[(r'/getIntro', MainHandler),(r'/getBooksByCa', MainHandler2), (r'/getBookInfo', MainHandler3),(r'/getSearchList', MainHandler4), (r'/getContent', MainHandler5)], autoreload=False, debug=False)
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8886)
    tornado.ioloop.IOLoop.instance().start()
