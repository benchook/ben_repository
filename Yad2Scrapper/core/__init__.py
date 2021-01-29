from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import random
from models.Motorcycle import Motorcycle

def only_divs(element):
    return element.tag_name == 'div'

def sleep_for_random_time():
    seconds = random.randrange(3, 8)
    print("sleeping for: ", seconds)
    time.sleep(seconds)

def createMotorcycleFromWebElement(web_element):
    id = web_element.get_attribute("item-id")
    html_element_id = web_element.get_attribute("id")
    html_element_id_splitted = html_element_id.split("_")
    html_element_id_splitted_index = html_element_id_splitted[2]

    hand = web_element.find_element_by_id("data_hand_" + html_element_id_splitted_index).text
    engine_capacity = web_element.find_element_by_id("data_engine_size_" + html_element_id_splitted_index).text
    price = web_element.find_element_by_id("feed_item_" + html_element_id_splitted_index + "_price").text
    year = web_element.find_element_by_id("data_year_" + html_element_id_splitted_index).text
    update_time = web_element.find_element_by_id("feed_item_" + html_element_id_splitted_index + "_date").text
    model = web_element.find_element_by_id("feed_item_" + html_element_id_splitted_index + "_title").text
    return Motorcycle(id, model, year, hand, engine_capacity, price, update_time)

if __name__ == "__main__":
    # Open browser
    PATH = "C:\Program Files (x86)\chromedriver.exe"
    driver = webdriver.Chrome(PATH)

    # browse
    driver.get("https://www.yad2.co.il/vehicles/motorcycles?manufacturer=124&model=1895")
    sleep_for_random_time()

    # get motorcycles
    #items = driver.find_elements_by_xpath("//*[@class='feeditem table']")
    items = driver.find_elements_by_xpath("//*[@class='feed_item feed_item-v4 accordion desktop']")
    motorcycles = list()
    for item in items:
        motorcycle = createMotorcycleFromWebElement(item)
        motorcycles.append(motorcycle)
        print(motorcycle.id)

    #driver.quit()


