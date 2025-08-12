import React from "react";
import { Layout, Typography } from 'antd';

const { Header, Content, Footer } = Layout;
const { Title } = Typography;

function AppLayout({ children }) {
    return (
        <Layout style={{ minHeight: '100vh', width: '100vw' }}>
            <Header style={{ display: 'flex', alignItems: 'center' }}>
                <Title level={3} style={{ color: 'white', margin: 0 }}>
                    Projeto
                </Title>
            </Header>
            <Content style={{ padding: '0 48px' }}>
                {children}
            </Content>
            <Footer style={{ textAlign: 'center' }}>
                Projeto de Estatisticas Musicais ©{new Date().getFullYear()} Criado por Rafael Gustavo Kammler!
            </Footer>
        </Layout>
    )
}

export default AppLayout;