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

::压缩 sourcePath文件夹 到 target.tar.gz 压缩包
tar -czvf ../target.tar.gz sourcePath
:: 解压缩 source.tar.gz 压缩包  到 targetPath文件夹
tar -xzvf  ../source.tar.gz -C ../targetPath