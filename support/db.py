from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, ForeignKey, UniqueConstraint, Index
from sqlalchemy.orm import sessionmaker, relationship
from sqlalchemy import create_engine
from sqlalchemy.schema import Sequence, CreateSequence
engine = create_engine("postgresql://peter.huang@127.0.0.1:5432/zhihu", echo=True)

Base = declarative_base()
Session = sessionmaker(bind=engine)
session = Session()

class Users(Base):
    __tablename__ = 'users'
    users_id_seq = Sequence('users_id_seq')
    id = Column(Integer, users_id_seq, server_default=users_id_seq.next_value())
    name = Column(String(255), primary_key=True)

class Topics(Base):
    __tablename__ = 'topics'
    topics_id_seq = Sequence('topics_id_seq')
    id = Column(Integer, topics_id_seq, server_default=topics_id_seq.next_value(), unique=True)
    title = Column(String(255), primary_key=True)
    url = Column(String(255))

class Pics(Base):

    __tablename__ = 'pics'

    id = Column(Integer, primary_key=True)
    star = Column(Integer)
    user_id = Column(ForeignKey('users.id'))
    user = relationship('Users')
    pic_url = Column(String(255))
    topic_id = Column(ForeignKey('topics.id'))
    topic = relationship('Topics')

    def __repr__(self):
        return "%s-%s" %(self.id, self.name)

def init_seq():
    engine.execute(CreateSequence(Sequence('topics_id_seq')))
    engine.execute(CreateSequence(Sequence('users_id_seq')))
    
def init_db():
    Base.metadata.create_all(engine)

def drop_db():
    Base.metadata.drop_all(engine)

def insert_user(name):
    user = Users()
    user.name = name
    session.add(user)
    session.flush()
    session.commit()
    return user

def insert_topic(title, url):
    topic = Topics()
    topic.title = title
    topic.url = url
    session.add(topic)
    session.flush()
    session.commit()
    return topic


def insert_db(star, user, pic_url, topic_id):
    pic = Pics()
    pic.star = star
    pic.user = user
    pic.pic_url = pic_url
    pic.topic_id = topic_id
    session.add(pic)
    session.flush()
    session.commit()
    return pic
