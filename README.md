virtualenv venv

source venv/bin/activate

pip3 install -r requirements.txt

python3 manage.py makemigrations

python3 manage.py migrate

python3 manage.py runserver



https://drive.google.com/file/d/1mOb4fGb4VJ_M7hfMhdiTHHt43V9oQq-L/view?usp=sharing
