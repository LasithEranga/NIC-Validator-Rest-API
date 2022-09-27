
from urllib.request import urlopen
import json
import random 
import faker

from faker import Faker
fake = Faker()
noOfRecords = 1000

months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"]
days =["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"]
years =['1931', '1932', '1933', '1934', '1935', '1936', '1937', '1938', '1939', '1940', '1941', '1942', '1943', '1944', '1945', '1946', '1947', '1948', '1949', '1950', '1951', '1952', '1953', '1954', '1955', '1956', '1957', '1958', '1959', '1960', '1961', '1962', '1963', '1964', '1965', '1966', '1967', '1968', '1969', '1970', '1971', '1972', '1973', '1974', '1975', '1976', '1977', '1978', '1979', '1980', '1981', '1982', '1983', '1984', '1985', '1986', '1987', '1988', '1989', '1990', '1991', '1992', '1993', '1994', '1995', '1996', '1997', '1998', '1999', '2000']
monthDays = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];



def randomBirthday():

  DOB = (random.choice(years) + "-" + random.choice(months) + "-" + 
  random.choice(days))
  return DOB

addresses = urlopen("https://names.drycodes.com/"+str(noOfRecords)+"?nameOptions=states&separator=space")
address_json = json.loads(addresses.read())
nationality = ["Sinhalese","Hindu","Islamic","Sinhalese"]
genderList = ["Male","Female","Female","Female"]

#create a new nic based on the birthday
#get the year last two digits set first no
#get the next three digits and calculate the days
#get the generated gender and add 500 or not

x = "INSERT INTO `user` (`id`, `nic`, `full_name`, `address`, `dob`, `nationality`, `gender`, `state`, `created_by`, `created_on`, `created_at`, `modified_by`, `modified_on`, `modified_at`, `future_column_1`, `future_column_2`, `future_column_3`) VALUES \
(NULL, '990811001V', '"+fake.first_name() +' '+ fake.last_name()+"', '"+address_json[0]+"' ,  '1999-03-21', '"+random.choice(nationality)+"',  'Male', 1, 'system', now() , now() , NULL , NULL, NULL, NULL, NULL, NULL)"
for i in range (1, noOfRecords):
  dob = randomBirthday()
  gender = random.choice(genderList)
  nic = dob[2:4]
  month = int(dob[5:7])
  daysToBirthday =  int(dob[8:])
  for k in range (0,month-1):
    daysToBirthday+=monthDays[k]
  
  if(gender == "Female"):
    nic += "{:03d}".format(daysToBirthday+500)
  else:
    nic += "{:03d}".format(daysToBirthday)

  
  x += ",(NULL, '"+ (nic+"" + "{:04d}".format(i) + "V") +"' , '"+fake.first_name() +' '+ fake.last_name()+"', '"+address_json[i]+"',  '"+dob+"', '"+random.choice(nationality)+"',  '"+gender+"', 1, 'system', now() , now() , NULL , NULL, NULL, NULL, NULL, NULL)"

print(x)