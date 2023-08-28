version="v1.3.1"

#echo "aaa/$version"

git add .
git commit -m "update $version"
git push github
git push gitee

#git tag $version
#git push github --tags
#git push gitee --tags