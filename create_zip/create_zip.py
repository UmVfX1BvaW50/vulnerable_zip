import zipfile

def zip():
    try:
        with open("test.txt", "r") as f:
            binary = f.read()
            zipFile = zipfile.ZipFile("test.zip", "a", zipfile.ZIP_DEFLATED)
            info = zipfile.ZipInfo("test.zip")
            zipFile.writestr("../test.txt", binary)
            zipFile.close()
    except IOError as e:
        raise e

if __name__ == '__main__':
    zip()
