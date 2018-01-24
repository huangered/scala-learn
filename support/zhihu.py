import time

class Zhihu:
    def __init__(self, driver):
        print("init zhihu client")
        self.driver = driver
    def login(self, username, password):
        msg = "login as %s" % (username)
        print(msg)
        self.driver.get("https://www.zhihu.com/signin")

        self.driver.find_element_by_name("username").send_keys(username)
        self.driver.find_element_by_name("password").send_keys(password)

        btn = self.driver.find_elements_by_css_selector("button.Button:nth-child(5)")[0]
        print(btn.get_attribute("class"))
        time.sleep(1)
        print("click")
        btn.click()

    def get_url(self, url):
        self.driver.get(url)

    def click_more(self):
        try:
            more = self.driver.find_elements_by_css_selector(".Question-mainColumn > div:nth-child(1) > a:nth-child(1)")
            print(more)
            more[0].click()
        except Exception as e:
            print(e)
            print("break")

    def scroll(self):
        print("scroll")
        self.driver.execute_script("""
            (function () {
                var y = document.body.scrollTop;
                var step = 500;
                window.scroll(0, y);
                var count = 2000;
                var cur = 0;
                function f() {
                    if (y < document.body.scrollHeight && cur<count) {
                        cur += 1;
                        console.log(cur)
                        y += step;
                        window.scroll(0, y);
                        setTimeout(f, 50);
                    }
                    else {
                        window.scroll(0, y);
                        document.title += "scroll-done";
                    }
                }
                setTimeout(f, 1000);
            })();
            """)

    def page_source(self):
        return self.driver.page_source