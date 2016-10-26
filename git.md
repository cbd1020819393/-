#github入门与实践


##git与github
github是使用Git的托管服务。是网络上提供Git仓库的一项服务。
git是开发者将源代码存入名叫“Git仓库”的资料库中并加以使用。

##集中型与分散型
集中型：只有一个仓库，例如SVN
分散型：多个仓库，例如Git


##使用GitHub的前期准备
- 创建账户
- 设置SSH Key
- 添加公开密钥

###创建账户
本人操作就是用自己的邮箱注册一个，然后在本地的git也使用相同的账户。然后生成公开密钥。

设置本地姓名和邮箱
<pre><code>
$ git config --global user.name "Firstname Lastname"
$ git config --global user.email "your_email@example.com"
</code></pre>

生成密钥的代码
<pre><code>
$ ssh-keygen -t rsa -C"your_email@example.com"
</code></pre>

###设置SSH Key
在github中把生成的公开密钥输入进去验证即可。

查看生成的公开密钥
<pre><code>
$ cat ~/.ssh/id_rsa.pub
ssh-rsa 公开密钥的内容 your_email@example.com
</code></pre>

然后在github中添加公钥。

###添加公开密钥

通过手中的私钥进行认证和通信
<pre><code>
$ ssh -T git@github.com
The authenticity of host 'github.com (207.97.227.239)' can't be
RSA key fingerprint is fingerprint值 .
Are you sure you want to continue connecting (yes/no)? 输入yes

Hi hirocastest! You've successfully authenticated, but GitHub does not
provide shell access.
</code></pre>

##通过实际操作学习Git
##基本操作
- git init----初始化仓库
- git status----查看仓库的状态
- git add----向暂存区中添加文件
- git commit----保存仓库的历史记录
- git log----查看提交日志
- git diff----查看更改前后的差别

git commit -am等同于git add 与git commit

###git init
本人感觉：随便找个空文件夹即可。初始化完得到.git隐藏文件夹。

###git commit
添加并且添加描述
<pre><code>git commit -m "具体描述随意"
</code></pre>

###git log
只显示信息的第一行
<pre><code>log --pretty=short
</code></pre>

只显示指定目录、文件的日志<pre><code>git log 指定文件
</code></pre>

显示文件的改动
<pre><code>git log -p
git log -p 指定文件
</code></pre>

###git diff
查看工作树和暂存区的差别
<pre><code>git diff
</code></pre>

查看工作树和最新提交的差别
<pre><code>git diff HEAD
</code></pre>

##分支的操作
分支的操作可以在不互相影响的情况下同时进行多个功能的开发。
每个分支的数据可以完全不一样。
###git branch——显示分支一览表
git branch可以将分支名列表显示，同时确定当前所在列表。
* 后面的是你当前的分支。
<pre><code>$ git branch
* master
</code></pre>

###git checkout -b——创建、切换分支
<pre><code>$ git checkout -b feature-A
Switched to a new branch 'feature-A'
</code></pre>

<pre><code>$ git branch feature-A
$ git checkout feature-A
</code></pre>
这两段代码作用相同。

###git checkout 
切换分支的功能。
<pre><code>$ git checkout master
Switched to branch 'master'
</code></pre>

###git checkout -
切换回上一个分支
<pre><code>$ git checkout -
Switched to branch 'feature-A'
</code></pre>


###特性（Topic）分支
特性分支：集中实现单一特性（主题），除此之外不进行任何作业的分支。在日常开发中，往往会创建数个特性分支，同时在此之外再保留一个随时可以发布软件的稳定分支。稳定分支的角色通常由 master 分支担当。


###主干分支
主干分支是刚才我们讲解的特性分支的原点，同时也是合并的终点。通常人们会用 master 分支作为主干分支。主干分支中并没有开发到一半的代码，可以随时供他人查看。有时我们需要让这个主干分支总是配置在正式环境中，有时又需要用标签 Tag 等创建版本信息，同时管理多个版本发布。拥有多个版本发布时，主干分支也有多个。

###git merge——合并分支
<pre><code>git merge --no-ff feature-A</code></pre>


###git log --graph——以图表形式查看分支
可以清楚地看到特性分支提交的内容已被合并。特性分支的创建以及合并也都很清楚。

##更改提交的操作
###git reset——回溯历史版本
Git的另一个特征是可以灵活操作历史版本。
要让仓库的 HEAD、暂存区、当前工作树回溯到指定状态，需要用到 git rest --hard命令。只要提供目标时间点的哈希值 A，就可以完全恢复至该时间点的状态。
<pre><code>$ git reset --hard fd0cbf0d4a25f747230694d95cac1be72d33441d 
HEAD is now at fd0cbf0 Add index</code></pre>

###git reflog
查看当前仓库执行过的操作日志。
在日志中，我们可以看到 commit、 checkout、 reset、 merge 等 Git 命
令的执行记录。
<pre><code>$ git reflog
95d2c17 HEAD@{0}: commit: Fix B
360b87b HEAD@{1}: checkout: moving from master to fix-B
360b87b HEAD@{2}: reset: moving to 360b87b712f57980704a37739a14bd6b7123422a
9a33320 HEAD@{3}: merge feature-A: Merge made by the 'recursive' strategy.
360b87b HEAD@{4}: checkout: moving from feature-A to master
a6c4e00 HEAD@{5}: checkout: moving from master to feature-A
360b87b HEAD@{6}: checkout: moving from feature-A to master
a6c4e00 HEAD@{7}: checkout: moving from master to feature-A
360b87b HEAD@{8}: checkout: moving from feature-A to master
a6c4e00 HEAD@{9}: commit: Add feature-A
360b87b HEAD@{10}: checkout: moving from master to feature-A
360b87b HEAD@{11}: commit: add index
4e9e597 HEAD@{12}: commit (initial): First commit
</code></pre>

###git commit --amend
修改提交信息。
<pre><code>$ git commit --amend
</code></pre>

###git rebase -i————压缩历史

##推送至远程仓库

###git remote add————添加远程仓库
git remote add origin 你要添加的git路径。
<pre><code>$ git remote add origin git@github.com:github-book/git-tutorial.git
</code></pre>
按照上述格式执行 git remote add命令之后， Git 会自动将git@github.com:github-book/git-tutorial.git远程仓库的名称设置为 origin（标识符）。

###git push————推送至远程仓库

1.推送至master分支
<pre><code>$ git push -u origin master
</code></pre>
u参数可以在推送的同时，将 origin 仓库的 master 分支设置为本地仓库当前分支的 upstream（上游）。添加了这个参数，将来运行 git pull命令从远程仓库获取内容时，本地仓库的这个分支就可以直接从 origin 的 master 分支获取内容，省去了另外添加参数的麻烦。

2.推送至master以外的分支
在本地建个新分支然后推送到远程仓库。

<pre><code>$ git push -u origin test-E                                                    
Total 0 (delta 0), reused 0 (delta 0)
To github.com:cbd1020819393/git-test.git
 * [new branch]      test-E -> test-E
Branch test-E set up to track remote branch test-E from origin.
</code></pre>

##从远程仓库获取
###git clone———— 获取远程仓库
<pre><code>$ git clone git@github.com:cbd1020819393/git-test.git
Cloning into 'git-test'...
remote: Counting objects: 19, done.
remote: Compressing objects: 100% (6/6), done.
remote: Total 19 (delta 2), reused 19 (delta 2), pack-reused 0
Receiving objects: 100% (19/19), done.
Resolving deltas: 100% (2/2), done.
</code></pre>
执行 git clone命令后我们会默认处于 master 分支下，同时系统会自动将 origin 设置成该远程仓库的标识符。

###git branch -a
<pre><code>$ git branch -a
* master
  remotes/origin/HEAD -> origin/master
  remotes/origin/master
  remotes/origin/test-E
</code></pre>
我们用 git branch -a命令查看当前分支的相关信息。添加 -a
参数可以同时显示本地仓库和远程仓库的分支信息。

###获取远程的分支
<pre><code>$ git checkout -b test-E origin/test-E
Branch test-E set up to track remote branch test-E from origin.
Switched to a new branch 'test-E'
</code></pre>
-b 参数的后面是本地仓库中新建分支的名称。为了便于理解，我
们仍将其命名为 test-E，让它与远程仓库的对应分支保持同名。

###向本地的test-E分支提交更改，推送test-E分支
从远程仓库获取 test-E 分支，在本地仓库中提交更改，再将
test-E 分支推送回远程仓库，通过这一系列操作，就可以与其他开发
者相互合作，共同培育 test-E 分支，实现某些功能。

###git pull———获取最新的远程仓库分支
<pre><code>$ git pull origin test-E
remote: Counting objects: 3, done.
remote: Compressing objects: 100% (2/2), done.
remote: Total 3 (delta 0), reused 3 (delta 0), pack-reused 0
Unpacking objects: 100% (3/3), done.
From github.com:cbd1020819393/git-test
 * branch            test-E     -> FETCH_HEAD
   4478b9d..9c5f2b6  test-E     -> origin/test-E
Updating 4478b9d..9c5f2b6
Fast-forward
 README.md | 1 +
 1 file changed, 1 insertion(+)
</code></pre>
通过这样的操作就可以更新到远程test-E的最新状态。本地仓库得到更新。如果两人同时修改了同一部分的源代码， push 时就很容易发生冲突。所以多名开发者在同一个分支中进行作业时，为减少冲突情况的发生，建议更频繁地进行 push 和 pull 操作。

