virtualenv venv
source venv/bin/activate
pip3 install -r requirements.txt
python3 manage.py makemigrations (этой команды там не было, но тебе будет нужна для запуска)
python3 manage.py migrate
python3 manage.py runserver
