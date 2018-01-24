#coding=utf-8
import zhihu
import time
import db
import os
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.action_chains import ActionChains

from bs4 import BeautifulSoup
import requests

def download(userInfo, star, title, imgs, topic_id):
    for img in imgs:
        url= img["src"]
        r = requests.get(url)
        name = title +"/" + str(star) + "-"+userInfo+"-"+url.split("/")[-1]
        len_content = len(r.content)/1024
        if len_content > 10: # only download size > 10kb
            print("download %s" % (url))
            with open(name, "wb") as code:
                code.write(r.content)
            db.insert_db(star, userInfo, url, topic_id)

def other(html_doc, url):
    soup = BeautifulSoup(html_doc)
    title = soup.title.string
    print(title)
    topic = db.insert_topic(title, url)
    if not os.path.exists(title):
        os.mkdir(title)
    list_items = soup.find_all("div", class_="List-item")
    print("list item size %s" % (len(list_items)))
    for list_item in list_items:
        star = ""
        user = ""
        voteUp=list_item.find_all("button", class_="VoteButton")
        if (len(voteUp)>0):
            base = 1
            star = voteUp[0].text
            if (star.endswith("K")):
                star = star[:len(star)-1]
                base = 1000
            star = int(float(star)*base)
            print("star %d" % (star))
        userInfo = list_item.find_all("a", class_="UserLink-link")
        print(userInfo)
        for u in userInfo:
            user = user + u.text
        print("user %s" % (user))
        imgs = list_item.find_all("img")
        download(user, star, title, imgs, topic.id)


username=""
password=""


driver = webdriver.Firefox()
client = zhihu.Zhihu(driver)
client.login(username, password)

#url="https://www.zhihu.com/question/68381376/answer/294944741"
#url = "https://www.zhihu.com/question/30502941/answer/295225332"
url = "https://www.zhihu.com/question/29279000"
#url = "https://www.zhihu.com/question/66313867/answer/242817245"
client.get_url(url)
client.click_more()

#client.scroll()
n = 10 * 60
print("sleep 180")
#time.sleep(n)

html_doc = client.page_source()
#download(html_doc)

other(html_doc, url)

text = input("close?")  # Python 3
if text == 'yes':
    driver.quit()
