::run bat in cmd
echo commit msg %1
echo export path %2
cd %2

echo 'git add .'
git add .
echo 'git commit -m "%1"'
git commit -m "%1"
echo 'git pull'
git pull
echo 'git push'
git push

