from urllib.request import urlopen
import json
import random 
import faker

months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", 
  "10", "11", "12"]
days =["01", "02", "03", "04", "05", "06", 
  "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", 
  "20", "21", "22", "23", "24", "25", "26", "27", "28"]
years =['1931', '1932', '1933', '1934', '1935', '1936', '1937', '1938', '1939', '1940', '1941', '1942', '1943', '1944', '1945', '1946', '1947', '1948', '1949', '1950', '1951', '1952', '1953', '1954', '1955', '1956', '1957', '1958', '1959', '1960', '1961', '1962', '1963', '1964', '1965', '1966', '1967', '1968', '1969', '1970', '1971', '1972', '1973', '1974', '1975', '1976', '1977', '1978', '1979', '1980', '1981', '1982', '1983', '1984', '1985', '1986', '1987', '1988', '1989', '1990', '1991', '1992', '1993', '1994', '1995', '1996', '1997', '1998', '1999', '2000']



def randomBirthday():

  DOB = (random.choice(years) + "-" + random.choice(months) + "-" + 
  random.choice(days))

  return DOB

names = urlopen('https://names.drycodes.com/5000?separator=space')
addresses = urlopen('https://names.drycodes.com/5000?nameOptions=states&separator=space')
names_json = json.loads(names.read())
address_json = json.loads(addresses.read())
nic_no = '99081'
nationality = ["Sinhalese","Hindu","Islamic","Sinhalese"]
gender = ["Male","Female","Female","Female"]

x = "INSERT INTO `user` (`id`, `nic`, `full_name`, `address`, `dob`, `nationality`, `gender`, `state`, `created_by`, `created_on`, `created_at`, `modified_by`, `modified_on`, `modified_at`, `future_column_1`, `future_column_2`, `future_column_3`) VALUES \
(NULL, '990711001V', '"+names_json[0]+"', '"+address_json[0]+"' ,  '"+randomBirthday()+"', '"+random.choice(nationality)+"',  '"+random.choice(gender)+"', 1, 'system', now() , now() , NULL , NULL, NULL, NULL, NULL, NULL)"
for i in range (1, 5000):
  x += ",(NULL, '"+ (nic_no+"" + "{:04d}".format(i) + "V") +"' , '"+names_json[i]+"', '"+address_json[i]+"',  '"+randomBirthday()+"', '"+random.choice(nationality)+"',  '"+random.choice(gender)+"', 1, 'system', now() , now() , NULL , NULL, NULL, NULL, NULL, NULL)"

print(x)