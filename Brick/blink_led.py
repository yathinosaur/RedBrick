from GPIOLibrary import GPIOProcessor
import time
import requests
from requests.adapters import HTTPAdapter
from requests.packages.urllib3.util.retry import Retry

session = requests.Session()
retry = Retry(connect=3, backoff_factor=0.5)
adapter = HTTPAdapter(max_retries=retry)
session.mount('http://', adapter)

session.get(url)

GP = GPIOProcessor()

r2 = requests.post('http://129.21.70.129:8081/newId', json = {
		"id": "mason",
		"blood_pressure": "100",
		"histamine_concentration": "1",
		"core_body_temperature": "36",
		"safe": "true"
	})

try:
    Pin29 = GP.getPin29()
    Pin29.input()

    Pin31 = GP.getPin31()
    Pin31.input()	

    Pin33 = GP.getPin33()
    Pin33.input()
	
    pinValue = Pin33.getValue()
    counter1a = 100
    counter2 = 1
    counter3 = 36

    while True:
		if Pin29.getValue() == 1:
			counter1a = counter1a + 5
		else:
			counter1a = counter1a - 5
		if counter1a >= 250:
			counter1a = 250
		elif counter1a <= 70:
			counter1a = 70
		counter1b = counter1a * 2 / 3
		print("Blood Pressure: " , counter1a , " / " , counter1b)

		if Pin31.getValue() == 1:
			counter2 = counter2 + 0.5
		else:
			counter2 = counter2 - 0.5
		if counter2 >= 16:
			counter2 = 16
		elif counter2 <= 0.5:
			counter2 = 0.5
		print("Histamine Concentration: " , counter2)

		if Pin33.getValue() == 1:
			counter3 = counter3 + 0.5
		else:
			counter3 = counter3 - 0.5
		if counter3 >= 40:
			counter3 = 40
		elif counter3 <= 22:
			counter3 = 22
		print("Core Body Temperature: " , counter3)

		safe = "true"
		if counter3 < 32 and counter1a < 86 and counter2 > 6.3:
			safe = "false"
		r2 = requests.post('http://129.21.70.129:8081/mason/update', json = {
			"blood_pressure": str(str(counter1a) + " / " + str(counter1b)),
			"histamine_concentration": str(counter2),
			"core_body_temperature": str(counter3),
			"safe": str(safe)
		})
		time.sleep(0.5)	

finally:
    GP.cleanup()
