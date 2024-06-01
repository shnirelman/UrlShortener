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
for i in range(0, 10):
    long  = url_generator()
    short = addUrl(long).text
    a.append((long, short))

for i in range(0, min(len(a), 10)):
    print(a[i])

start = time.time()
requests.get('http://localhost:8080/url/61t3Hc6')
finish = time.time()
print('test query: 1st access: time = ', (finish - start))
start = time.time()
requests.get('http://localhost:8080/url/61t3Hc6')
finish = time.time()
print('test query: 2nd access: time = ', (finish - start))


start = time.time()

for p in a:
    getUrlByFullLink(p[1])

finish = time.time()

print('1st access: average time per request = ', (finish - start) / len(a))

start = time.time()

for p in a:
    getUrlByFullLink(p[1])

finish = time.time()


print('2nd access: average time per request = ', (finish - start) / len(a))

for i in range(0, min(len(a), 10)):
    res = getUrlByFullLink(a[i][1]).text
    print(a[i][0], res)
