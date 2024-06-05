import requests
import string
import random
import time

def addUrl(s):
    data = { "longForm": s}
    r = requests.post('http://localhost:8080/url/create', json = data)
    return r

def getUrlByFullLink(s):
    r = requests.get(s)
    return r

def string_generator(size = 6, chars = string.ascii_lowercase + string.digits):
    return ''.join(random.choice(chars) for _ in range(size))

def url_generator():
    return 'http://' + string_generator() + '.' + string_generator(3, string.ascii_lowercase)

a = []

start = time.time()
for i in range(0, 100):
    long  = url_generator()
    short = addUrl(long).text
    a.append((long, short))
finish = time.time()

print("average creating time: ", (finish - start) / len(a))

for i in range(0, min(len(a), 10)):
    print(a[i])


global_start = time.time()

s = 0

for p in a:
    #start = time.time()
    getUrlByFullLink(p[1])
    #finish = time.time()
    #s += finish - start

global_finish = time.time()

print('1st access: average time per request = ', (global_finish - global_start) / len(a))
#print('1st access: average time per request = ', s / len(a))

global_start = time.time()

s = 0

for p in a:
    #start = time.time()
    getUrlByFullLink(p[1])
    #finish = time.time()
    #s += finish - start

global_finish = time.time()

print('2st access: average time per request = ', (global_finish - global_start) / len(a))
#print('2st access: average time per request = ', s / len(a))

#for i in range(0, min(len(a), 10)):
#    res = getUrlByFullLink(a[i][1]).text
#    print(a[i][0], res)
